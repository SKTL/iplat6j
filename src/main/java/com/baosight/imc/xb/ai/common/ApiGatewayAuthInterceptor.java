package com.baosight.imc.xb.ai.common;


import com.baosight.imc.xb.ai.utils.AuthUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class ApiGatewayAuthInterceptor implements Interceptor {

    private final String appId;
    private final String appSecret;
    private final String assistantCode;

    public ApiGatewayAuthInterceptor(String appId, String appSecret, String assistantCode) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.assistantCode = assistantCode;
    }

    public ApiGatewayAuthInterceptor(String appId, String appSecret) {
        this(appId, appSecret, null);
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        String url;
        if ("GET".equalsIgnoreCase(chain.request().method())) {
            url = AuthUtils.assembleRequestUrl(chain.request().url().toString(), appId, appSecret);
        } else {
            url = AuthUtils.assemblePostRequestUrl(chain.request().url().toString(), appId, appSecret);
        }
        Assert.hasText(url, "url auth error, url is empty");
        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(url))
                .newBuilder();
        if (Objects.nonNull(assistantCode)) {
            builder.addQueryParameter("assistantCode", assistantCode);
        }
        Request request = chain.request().newBuilder()
                .url(builder.build())
                .build();
        return chain.proceed(request);
    }
}
