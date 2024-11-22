package com.baosight.imc.xb.ai.common;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baosight.iplat4j.core.ei.EiInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AgentChatWithoutSdkTest {
    /**
     * 开发平台访问地址
     */
    String baseUrl = "http://10.83.64.7:30009";
    /**
     * 应用管理 -> 应用详情 -> AppID
     */
    String appId = "1D33262FC75B4C2FA289";
    /**
     * 应用管理 -> 应用详情 -> App Secret Key
     */
    String appSecret = "1F7695FBDBEF4930A16F54B1A69F2746";
    /**
     * 应用管理 -> 应用详情 -> 关联智能体 -> 智能体编码
     */
    String assistantCode = "dialog@1844608068898754561";
    /**
     * 会话接口
     */
    String chatEndpoint = "openapi/flames/api/v1/chat";
    /**
     * 输入的内容
     */
    String content = "2024年出台了哪些楼市政策";

    CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final int NORMAL = 1000;
    private static final int NO_CODE = 1005;

    @Test
    public void testAgent() throws Exception {
        // 1. 构建请求client
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .addInterceptor(new ApiGatewayAuthInterceptor(appId, appSecret, assistantCode));
        //服务若未开启https,则不需要信任全部证书
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier((hostname, session) -> true);
        OkHttpClient okHttpClient = builder.build();

        // 2. 构建消息的请求体
        JSONObject chatRequest = new JSONObject()
                .fluentPut("header",//头信息
                        new JSONObject()
                                .fluentPut("mode", 0)
                                .fluentPut("appId", appId)
                                .fluentPut("assistantCode", assistantCode))
                .fluentPut("payload",//负载信息
                        new JSONObject()
                                .fluentPut("text",
                                        Collections.singletonList(
                                                new JSONObject()
                                                        .fluentPut("role", "user")
                                                        .fluentPut("content", content)
                                                        .fluentPut("content_type", "text")
                                        )
                                )
                )
                .fluentPut("parameter",
                        new JSONObject()
                                .fluentPut("userId", "178711")
                                .fluentPut("userName", "赵晶temp")
                );
        //3. 基于网关子服务和鉴权,处理会话接口地址
        EiInfo outInfo = new EiInfo();
        Map<String, Object> map = new HashMap<>();
        Request request = new Request.Builder().url(baseUrl + File.separator + chatEndpoint).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                log.debug("onOpen");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                log.debug("onMessage: {}", text);
            }

            @SneakyThrows
            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                onEvent(webSocket, code, reason, "onClosing");
            }

            @SneakyThrows
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                onEvent(webSocket, code, reason, "onClosed");
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
                String responseBody;
                if (Objects.nonNull(response) && Objects.nonNull(response.body())) {
                    try {
                        responseBody = response.body().string();
                        JSONObject body = JSON.parseObject(responseBody);
                        t = new ProtocolException(body.toString());
                    } catch (IOException e) {
                        t = e;
                    }
                }
                log.error("onFailure: {},{}", t.getMessage(), t.getClass());
                countDownLatch.countDown();
            }

            public void onEvent(@NotNull WebSocket webSocket, int code, String reason, String event) throws Exception {
                log.debug("session {} . code:{}, reason:{}", event, code, reason);
                if (NO_CODE != code && NORMAL != code) {
                    // 异常关闭连接
                    throw new Exception(String.format("%s error:%s. code:%s", event, reason, code));
                } else {
                    log.debug("completed...");
                }
                try {
                    webSocket.close(code, reason);
                } catch (Exception e) {
                    log.error("{} error.{}", event, e.getMessage());
                }
            }
        });
        //4.发送会话内容
        log.debug("chat send chatRequest = {}", chatRequest);
        webSocket.send(chatRequest.toString());
        //5. 等待结果打印
        countDownLatch.await(600 * 1000, TimeUnit.MILLISECONDS);
        log.debug("end, receive message counter.");
        //6. 关闭连接
        webSocket.close(1000, "NORMAL_CLOSURE");
        okHttpClient.dispatcher().executorService().shutdown();
        log.debug("close...");
    }
}
