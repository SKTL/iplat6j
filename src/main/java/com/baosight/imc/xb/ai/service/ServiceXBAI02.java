package com.baosight.imc.xb.ai.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baosight.imc.xb.ai.common.ApiGatewayAuthInterceptor;
import com.baosight.imc.xb.ai.constant.AssistantCodeEnums;
import com.baosight.imc.xb.ai.constant.IapConstants;
import com.baosight.imc.xb.ai.domain.XBAI02;
import com.baosight.imc.xb.ai.domain.XBAI0201;
import com.baosight.imc.xb.ai.domain.XBAI0202;
import com.baosight.imc.xb.ai.domain.XBAI03;
import com.baosight.iplat4j.core.data.id.UUIDHexIdGenerator;
import com.baosight.iplat4j.core.ei.EiBlock;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceEPBase;
import com.baosight.iplat4j.core.util.DateUtils;
import com.baosight.iplat4j.core.util.UserUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class ServiceXBAI02 extends ServiceEPBase {
    private static final String RESULT_BLOCK_B = "resultB";
    private static final String QUERY_BLOCK_B = "inqub_status";


    /**
     * 对话
     * 接口：S_XB_AI_0201
     * @param inInfo
     */
    public EiInfo chat(EiInfo inInfo) throws Exception {
        String sessionId = inInfo.getString("sessionId");
        String content = inInfo.getString("content");
        String intention = inInfo.getString("intention");
        String userId = inInfo.getString("userId");
        String userName = inInfo.getString("userName");

        if (StringUtils.isEmpty(intention)) {
            StringBuilder str = new StringBuilder();
            // 调用任务链进行意图识别
            List<JSONObject> intentionPayloadList = chatWithXunfeiApp(inInfo, IapConstants.INTENTION_ASSISTANT_CODE);
            for (JSONObject payload : intentionPayloadList) {
                JSONObject choices = payload.getJSONObject("choices");
                if (choices == null) {
                    continue;
                }
                List<JSONObject> text = choices.getList("text", JSONObject.class);
                if (text == null) {
                    continue;
                }
                for (JSONObject jsonObject : text) {
                    if ("text".equals(jsonObject.getString("content_type"))) {
                        str.append(jsonObject.getString("content"));
                    }
                }
            }
            intention = str.toString().replaceAll("<end>", "");
        }
        // 根据意图识别类型获取智能体编码
        String assistantCode = AssistantCodeEnums.getCodeByIntention(intention);
        if (assistantCode == null) {
            assistantCode = AssistantCodeEnums.INTELLIGENCE_INSIGHT.getAssistantCode();
            intention = AssistantCodeEnums.INTELLIGENCE_INSIGHT.getIntention();
        }

        List<JSONObject> outPayloadList = chatWithXunfeiApp(inInfo, assistantCode);
        EiInfo outInfo = handlePayloadList(outPayloadList);
        outInfo.set("intention", intention);
        // 会话记录
        if (StringUtils.isEmpty(sessionId)) {
            insertSessionHistory(userId, userName, outInfo.getString("sessionId"), content, intention);
        }
        insertUserChat(inInfo, outInfo);
        insertAssistantChat(inInfo, outInfo);
        return outInfo;
    }

    /**
     * 历史会话列表
     * 接口：S_XB_AI_0202
     */
    public EiInfo chatList(EiInfo inInfo) {
        // 接收入参
        String userId = inInfo.getString("userId");
        String keyword = inInfo.getString("keyword");
        String intention = inInfo.getString("intention");
        int page = inInfo.getInt("page");
        int size = inInfo.getInt("size");
        // 查询数据库
        EiInfo queryInfo = new EiInfo();
        EiBlock queryBlock = queryInfo.addBlock(EiConstant.queryBlock);
        queryBlock.setCell(0, "userId", userId);
        queryBlock.setCell(0, "sessionTitle", keyword);
        queryBlock.setCell(0, "intention", intention);
        queryBlock.setCell(0, "delFlag", "0");
        queryBlock.setCell(0, "orderBy", "REC_CREATE_TIME desc");
        EiBlock resultBlock = queryInfo.addBlock(EiConstant.resultBlock);
        resultBlock.set(EiConstant.offsetStr, size * (page - 1));
        resultBlock.set(EiConstant.limitStr, size);
        resultBlock.set(EiConstant.isCountFlag, "true");
        EiInfo queryResult = super.query(queryInfo, "XBAI02.query", new XBAI02());
        // 整合结果
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < queryResult.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            Map<String, String> row = new HashMap<>();
            row.put("sessionId", queryResult.getCellStr(EiConstant.resultBlock, i, "sessionId"));
            row.put("sessionTitle", queryResult.getCellStr(EiConstant.resultBlock, i, "sessionTitle"));
            row.put("intention", queryResult.getCellStr(EiConstant.resultBlock, i, "intention"));
            row.put("createTime", queryResult.getCellStr(EiConstant.resultBlock, i, "recCreateTime"));
            list.add(row);
        }
        EiInfo outInfo = new EiInfo();
        outInfo.set("list", list);
        outInfo.set("count", queryResult.getBlock(EiConstant.resultBlock).get("count"));
        return outInfo;
    }

    /**
     * 历史会话详情
     * 接口：S_XB_AI_0203
     */
    public EiInfo chatDetail(EiInfo inInfo) throws Exception {
        String sessionId = inInfo.getString("sessionId");
        // 查询数据库
        EiInfo queryInfo = new EiInfo();
        EiBlock queryBlock = queryInfo.addBlock(EiConstant.queryBlock);
        queryBlock.setCell(0, "sessionId", sessionId);
        queryBlock.setCell(0, "orderBy", "REC_CREATE_TIME asc");
        EiInfo queryResult = super.query(queryInfo, "XBAI0201.query", new XBAI0201());
        // 整合结果
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < queryResult.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            Map<String, Object> row = new HashMap<>();
            row.put("role", queryResult.getCellStr(EiConstant.resultBlock, i, "role"));
            row.put("chatId", queryResult.getCellStr(EiConstant.resultBlock, i, "chatId"));
            row.put("content", JSONObject.parseObject(queryResult.getCellStr(EiConstant.resultBlock, i, "content")));
            row.put("evaluate", queryResult.getCellStr(EiConstant.resultBlock, i, "evaluate"));
            row.put("createTime", queryResult.getCellStr(EiConstant.resultBlock, i, "recCreateTime"));
            list.add(row);
        }
        EiInfo outInfo = new EiInfo();
        outInfo.set("list", list);
        return outInfo;
    }

    /**
     * 删除历史会话
     * 接口：S_XB_AI_0204
     */
    public EiInfo deleteChat(EiInfo inInfo) {
        String sessionId = inInfo.getString("sessionId");
        EiInfo deleteInfo = new EiInfo();
        deleteInfo.setCell(EiConstant.resultBlock, 0, "sessionId", sessionId);
        super.delete(deleteInfo, "XBAI02.delete");
        return new EiInfo();
    }

    /**
     * 上传文件
     * 接口：S_XB_AI_0205
     */
    public EiInfo uploadFile(EiInfo inInfo) throws Exception {
        String fileName = inInfo.getString("fileName");
        String file = inInfo.getString("file");
        // 1. 构建请求client
        OkHttpClient okHttpClient = getHttpClient(IapConstants.INTENTION_ASSISTANT_CODE);
        // 2. 构建消息的请求体
        JSONObject uploadRequest = new JSONObject()
                .fluentPut("payload",// 负载信息
                        new JSONObject()
                                .fluentPut("fileName", fileName)
                                .fluentPut("file", file)
                );
        RequestBody requestBody = RequestBody.create(uploadRequest.toString(), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(IapConstants.BASE_URL + File.separator + IapConstants.UPLOAD_ENDPOINT)
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        log.info("okHttpClient call url: {}, response:{}", request.url(), response);
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        JSONObject responseBody = JSONObject.parseObject(response.body().string());
        EiInfo outInfo = new EiInfo();
        outInfo.set("fileId", responseBody.getJSONObject("payload").getString("id"));
        return outInfo;
    }

    /**
     * 下载文件
     * 接口：S_XB_AI_0206
     */
    public EiInfo downloadFile(EiInfo inInfo) throws Exception {
        String fileId = inInfo.getString("fileId");
        EiInfo outInfo = new EiInfo();
        // 请求讯飞接口
        OkHttpClient okHttpClient = getHttpClient(IapConstants.INTENTION_ASSISTANT_CODE);
        Request request = new Request.Builder()
                .url(StringUtils.replace(IapConstants.BASE_URL + File.separator + IapConstants.DOWNLOAD_ENDPOINT, "{fileId}", fileId))
                .get()
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            log.debug("okHttpClient call url: {}, response:{}", request.url(), response);
            // 处理响应数据（保存文件）
            try (ResponseBody body = handleStreamResponse(response)) {
                InputStream inputStream = body.byteStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] bytes = outputStream.toByteArray();
                outInfo.set("bytes", Arrays.toString(bytes));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outInfo;
    }

    /**
     * S_XB_AI_0207：智能员工助理反馈
     * @param inInfo
     * @return
     */
    public EiInfo chatFeedback(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        try {
            String userId = inInfo.getString("userId");
            String userName = inInfo.getString("userName");
            String chatId = inInfo.getString("chatId");// 当前对话ID
            String evaluate = inInfo.getString("evaluate");// 反馈内容ID：(1001,1002,1003)
            String evaluateId = inInfo.getString("evaluateId");// 反馈内容ID：(1001,1002,1003)
            String evaluateContent = inInfo.getString("evaluateContent");// 反馈其他内容
            Map<String, String> paramsMap = new HashMap<>();
            if (StringUtils.isBlank(chatId)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("当前对话ID不可为空！");
                return outInfo;
            }
            if (StringUtils.isBlank(evaluateId)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("反馈内容ID不可为空！");
                return outInfo;
            }
            if (StringUtils.isBlank(evaluate)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("评价类别不可为空！");
                return outInfo;
            }
            if ("1".equals(evaluate) && StringUtils.isNotBlank(evaluateContent)) {
                evaluateId += ",1000";
            } else if ("2".equals(evaluate) && StringUtils.isNotBlank(evaluateContent)) {
                evaluateId += ",2000";
            }
            paramsMap.put("recReviseTime", DateUtils.curDateMselStr17());
            paramsMap.put("recRevisor", userId);
            paramsMap.put("recRevisorName", userName);
            paramsMap.put("evaluate", evaluate);
            paramsMap.put("evaluateId", evaluateId);
            paramsMap.put("evaluateContent", evaluateContent);
            paramsMap.put("chatId", chatId);
            paramsMap.put("role", IapConstants.ASSISTANT_ROLE);
            dao.update("XBAI0201.updateByChatId", paramsMap);
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
            outInfo.setMsg("反馈成功！");
        } catch (Exception e) {
            log.error("error.{}", e.getMessage());
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(e.getMessage());
        }
        return outInfo;
    }

    /**
     * 查询交互式数据查询多轮对话
     * 接口：S_XB_AI_0208
     */
    public EiInfo interactiveDataQueryHistory(EiInfo inInfo) {
        String sessionId = inInfo.getString("sessionId");
        EiInfo outInfo = new EiInfo();
        List<String> historyList = new ArrayList<>();
        if (StringUtils.isEmpty(sessionId)) {
            outInfo.set("history", historyList);
            return outInfo;
        }
        // 查询数据库
        EiInfo queryInfo = new EiInfo();
        EiBlock queryBlock = queryInfo.addBlock(EiConstant.queryBlock);
        queryBlock.setCell(0, "sessionId", sessionId);
        queryBlock.setCell(0, "orderBy", "REC_CREATE_TIME desc");
        EiBlock resultBlock = queryInfo.addBlock(EiConstant.resultBlock);
        resultBlock.set(EiConstant.offsetStr, 0);
        resultBlock.set(EiConstant.limitStr, 6);
        EiInfo queryResult = super.query(queryInfo, "XBAI0201.query", new XBAI0201());
        // 提取会话内容
        List<String> list = new ArrayList<>();
        for (int i = 0; i < queryResult.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            try {
                String role = queryResult.getCellStr(EiConstant.resultBlock, i, "role");
                String contentStr = queryResult.getCellStr(EiConstant.resultBlock, i, "content");
                JSONObject content = JSONObject.parseObject(contentStr);
                if (IapConstants.USER_ROLE.equals(role)) {
                    String question = content.getString("content");
                    list.add("用户：" + question);
                } else if (IapConstants.ASSISTANT_ROLE.equals(role)) {
                    JSONArray dataList = content.getJSONArray("contents");
                    for (JSONObject data : dataList.toJavaList(JSONObject.class)) {
                        if ("data".equals(data.getString("contentType"))) {
                            String answer = data.getJSONObject("content").getJSONObject("result").getString("answer");
                            list.add("模型：" + answer);
                        }
                    }
                }
            } catch (Exception ignore) {
            }
        }
        Collections.reverse(list);
        outInfo.set("history", list);
        return outInfo;
    }

    public List<JSONObject> chatWithXunfeiApp(EiInfo inInfo, String assistantCode) throws Exception {
        String sessionId = inInfo.getString("sessionId");
        String content = inInfo.getString("content");
        String userId = inInfo.getString("userId");
        String userName = inInfo.getString("userName");
        Object fileListObj = inInfo.get("fileList");
        String isOnline = inInfo.getString("isOnline");

        List<JSONObject> list = new ArrayList<>();

        // 1. 构建请求client
        CountDownLatch countDownLatch = new CountDownLatch(1);
        OkHttpClient okHttpClient = getHttpClient(assistantCode);
        // 2. 构建消息的请求体
        JSONObject chatRequest = new JSONObject()
                .fluentPut("header",// 头信息
                        new JSONObject()
                                .fluentPut("mode", 0)
                                .fluentPut("appId", IapConstants.APP_ID)
                                .fluentPut("assistantCode", assistantCode))
                .fluentPut("parameter",
                        new JSONObject()
                                .fluentPut("sessionId", sessionId)
                                .fluentPut("userid", userId)
                                .fluentPut("username", userName)
                                .fluentPut("isOnline", StringUtils.isNotEmpty(isOnline) ? isOnline : "1")
                )
                .fluentPut("payload",// 负载信息
                        new JSONObject()
                                .fluentPut("sessionId", StringUtils.isNotEmpty(sessionId) ? sessionId : "")
                                .fluentPut("text",
                                        Arrays.asList(
                                                new JSONObject()
                                                        .fluentPut("role", "user")
                                                        .fluentPut("content", content)
                                                        .fluentPut("content_type", "text")
                                        )
                                )
                );
        if (fileListObj instanceof List) {
            List fileList = (List) fileListObj;
            if (CollectionUtils.isNotEmpty(fileList)) {
                Map firstFile = (Map) fileList.get(0);
                chatRequest.getJSONObject("payload").getJSONArray("text").fluentAdd(new JSONObject()
                        .fluentPut("content_type", "form_input")
                        .fluentPut("type", "file")
                        .fluentPut("formInput",
                                new JSONObject()
                                        .fluentPut("parameters",
                                                Collections.singletonList(
                                                        new JSONObject()
                                                                .fluentPut("key", "file")
                                                                .fluentPut("name", "文件")
                                                                .fluentPut("style", "upload")
                                                                .fluentPut("type", "string")
                                                                .fluentPut("value", firstFile.get("path"))
                                                                .fluentPut("fileList", fileList)
                                                )
                                        )
                        )
                );
            }
        }
        log.info(chatRequest.toString());
        // 3. 基于网关子服务和鉴权,处理会话接口地址
        Request request = new Request.Builder().url(IapConstants.BASE_URL + File.separator + IapConstants.CHAT_ENDPOINT).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                log.info("onOpen");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                log.info("onMessage: {}", text);
                JSONObject message = JSONObject.parseObject(text);
                JSONObject payload = message.getJSONObject("payload");
                payload.fluentPut("intention", inInfo.getString("intention"));
                list.add(payload);
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
                log.info("session {} . code:{}, reason:{}", event, code, reason);
                if (IapConstants.NO_CODE != code && IapConstants.NORMAL_CODE != code) {
                    // 异常关闭连接
                    throw new Exception(String.format("%s error:%s. code:%s", event, reason, code));
                } else {
                    log.info("completed...");
                }
                try {
                    webSocket.close(code, reason);
                } catch (Exception e) {
                    log.error("{} error.{}", event, e.getMessage());
                }
            }
        });
        // 4.发送会话内容
        log.info("chat send chatRequest = {}", chatRequest);
        webSocket.send(chatRequest.toString());
        // 5. 等待结果打印
        countDownLatch.await(600 * 1000, TimeUnit.MILLISECONDS);
        log.info("end, receive message counter.");
        // 6. 关闭连接
        webSocket.close(1000, "NORMAL_CLOSURE");
        okHttpClient.dispatcher().executorService().shutdown();
        log.info("close...");

        return list;
    }

    private OkHttpClient getHttpClient(String assistantCode) throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .addInterceptor(new ApiGatewayAuthInterceptor(IapConstants.APP_ID, IapConstants.APP_SECRET, assistantCode));
        // 服务若未开启https,则不需要信任全部证书
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
        return builder.build();
    }

    private ResponseBody handleStreamResponse(Response response) throws Exception {
        if (!response.isSuccessful()) {
            throw new RuntimeException(response.message());
        }
        // 处理响应数据
        if (response.body() == null) {
            throw new IOException("response body is null!");
        }
        return response.body();
    }

    private EiInfo handlePayloadList(List<JSONObject> payloadList) {
        EiInfo outInfo = new EiInfo();
        Map<String, Object> contentMap = new HashMap<>();
        for (JSONObject payload : payloadList) {
            if (!payload.containsKey("choices")) {
                continue;
            }
            if (Objects.equals(payload.getJSONObject("choices").getString("status"), "0")) {
                outInfo.set("sessionId", payload.getString("sessionId"));
                outInfo.set("chatId", payload.getJSONObject("context").getString("chatId"));
            }
            JSONArray texts = payload.getJSONObject("choices").getJSONArray("text");
            if (!texts.isEmpty()) {
                for (int i = 0; i < texts.size(); i++) {
                    JSONObject o = texts.getJSONObject(i);
                    if (!"assistant".equals(o.getString("role"))) {
                        continue;
                    }
                    if ("text".equals(o.getString("content_type"))) {
                        String content = contentMap.get("text") != null ? String.valueOf(contentMap.get("text")) : "";
                        content += o.getString("content");
                        contentMap.put("text", content);
                    } else if ("progress".equals(o.getString("content_type"))) {
                        JSONObject content = o.getJSONObject("progress");
                        if (content.containsKey("skill")) {
                            JSONObject skill = content.getJSONObject("skill");
                            if (skill.containsKey("skillOutput")) {
                                try {
                                    JSONObject output = JSON.parseObject(skill.getString("skillOutput")).getJSONObject("output");
                                    contentMap.putAll(output);
                                } catch (Exception e) {
                                    contentMap.put("skill", skill.getString("skillOutput"));
                                }
                            }
                        }
                    } else {
                        contentMap.put(o.getString("content_type"), o.get(o.getString("content_type")));
                    }
                }
            }
            if (Objects.equals(payload.getJSONObject("choices").getString("status"), "2")) {
                if (contentMap.containsKey("text")) {
                    contentMap.put("text", String.valueOf(contentMap.get("text")).replaceAll("<end>", ""));
                }
                List<Map<String, Object>> contents = new ArrayList<>();
                for (Map.Entry<String, Object> entry : contentMap.entrySet()) {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("contentType", entry.getKey());
                    obj.put("content", entry.getValue());
                    contents.add(obj);
                }
                outInfo.set("contents", contents);
                log.info("outInfo: {}", outInfo);
            }
        }
        return outInfo;
    }

    private void insertSessionHistory(String userId, String userName, String sessionId, String sessionTitle, String intention) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)) {
            return;
        }
        EiInfo eiInfo = new EiInfo();
        eiInfo.setCell(EiConstant.resultBlock, 0, "uuid", UUIDHexIdGenerator.generate().toString());
        eiInfo.setCell(EiConstant.resultBlock, 0, "userId", userId);
        eiInfo.setCell(EiConstant.resultBlock, 0, "userName", userName);
        eiInfo.setCell(EiConstant.resultBlock, 0, "sessionId", sessionId);
        eiInfo.setCell(EiConstant.resultBlock, 0, "sessionTitle", sessionTitle);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreateTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreator", userId);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreatorName", userName);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recReviseTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisor", userId);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisorName", userName);
        eiInfo.setCell(EiConstant.resultBlock, 0, "segNo", " ");
        eiInfo.setCell(EiConstant.resultBlock, 0, "archiveFlag", "0");
        eiInfo.setCell(EiConstant.resultBlock, 0, "delFlag", "0");
        eiInfo.setCell(EiConstant.resultBlock, 0, "intention", intention);
        super.insert(eiInfo, "XBAI02.insert");
    }

    private void insertUserChat(EiInfo inInfo, EiInfo outInfo) {
        if (StringUtils.isEmpty(inInfo.getString("userId")) || StringUtils.isEmpty(inInfo.getString("userName"))) {
            return;
        }
        Map<String, Object> content = new HashMap<>();
        content.put("content", inInfo.getString("content"));
        content.put("fileList", inInfo.get("fileList"));
        EiInfo eiInfo = new EiInfo();
        eiInfo.setCell(EiConstant.resultBlock, 0, "uuid", UUIDHexIdGenerator.generate().toString());
        eiInfo.setCell(EiConstant.resultBlock, 0, "sessionId", outInfo.getString("sessionId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "chatId", outInfo.getString("chatId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "role", IapConstants.USER_ROLE);
        eiInfo.setCell(EiConstant.resultBlock, 0, "content", JSONObject.toJSONString(content));
        eiInfo.setCell(EiConstant.resultBlock, 0, "evaluate", IapConstants.NO_EVALUATE);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreateTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreator", inInfo.getString("userId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreatorName", inInfo.getString("userName"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recReviseTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisor", inInfo.getString("userId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisorName", inInfo.getString("userName"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "segNo", " ");
        eiInfo.setCell(EiConstant.resultBlock, 0, "archiveFlag", "0");
        eiInfo.setCell(EiConstant.resultBlock, 0, "delFlag", "0");
        super.insert(eiInfo, "XBAI0201.insert");
    }

    private void insertAssistantChat(EiInfo inInfo, EiInfo outInfo) {
        if (StringUtils.isEmpty(inInfo.getString("userId")) || StringUtils.isEmpty(inInfo.getString("userName"))) {
            return;
        }
        EiInfo eiInfo = new EiInfo();
        eiInfo.setCell(EiConstant.resultBlock, 0, "uuid", UUIDHexIdGenerator.generate().toString());
        eiInfo.setCell(EiConstant.resultBlock, 0, "sessionId", outInfo.getString("sessionId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "chatId", outInfo.getString("chatId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "role", IapConstants.ASSISTANT_ROLE);
        eiInfo.setCell(EiConstant.resultBlock, 0, "content", JSONObject.toJSONString(outInfo.getAttr()));
        eiInfo.setCell(EiConstant.resultBlock, 0, "evaluate", IapConstants.NO_EVALUATE);
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreateTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreator", inInfo.getString("userId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recCreatorName", inInfo.getString("userName"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recReviseTime", DateUtils.curDateMselStr17());
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisor", inInfo.getString("userId"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "recRevisorName", inInfo.getString("userName"));
        eiInfo.setCell(EiConstant.resultBlock, 0, "segNo", " ");
        eiInfo.setCell(EiConstant.resultBlock, 0, "archiveFlag", "0");
        eiInfo.setCell(EiConstant.resultBlock, 0, "delFlag", "0");
        super.insert(eiInfo, "XBAI0201.insert");
    }

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        List<XBAI0202> evaluates = dao.query("XBAI0202.query", new HashMap<>(), 0, -999999);
        Map<String, String> evaluateMap = evaluates.stream().collect(Collectors.toMap(XBAI0202::getEvaluateId, XBAI0202::getEvaluateType));
        EiInfo eiInfo = super.initLoad(inInfo, new XBAI02());
        eiInfo.set("evaluateMap", evaluateMap);
        return eiInfo;
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        inInfo.getBlock(EiConstant.queryBlock).setCell(0, "delFlag", 0);
        return super.query(inInfo, "XBAI02.query", new XBAI03());
    }

    public EiInfo queryInfo(EiInfo inInfo) {
        inInfo.getBlock(QUERY_BLOCK_B).setCell(0, "delFlag", 0);
        return super.query(inInfo, "XBAI0201.query", new XBAI02(), false, null, QUERY_BLOCK_B, RESULT_BLOCK_B, RESULT_BLOCK_B);
    }

}
