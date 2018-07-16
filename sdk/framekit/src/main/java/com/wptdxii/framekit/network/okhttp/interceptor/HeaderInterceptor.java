package com.wptdxii.framekit.network.okhttp.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加统一的请求头
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .addHeader("Accept", "application/vnd.github.v3.full+json")
                .addHeader("User-Agent", "C-RetrofitBean-Sample-App")
                .build();

        return chain.proceed(request);
    }
}
