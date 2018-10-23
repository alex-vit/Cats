package com.alexvit.cats.common.data.api;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;

public class InsertApiKeyInterceptor implements Interceptor {

    private static final String HEADER_API_KEY = "x-api-key";

    private final String apiKey;

    public InsertApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request request = originalRequest.newBuilder()
                .addHeader(HEADER_API_KEY, apiKey)
                .build();

        return chain.proceed(request);
    }

}
