package com.alexvit.cats.data.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class InsertApiKeyInterceptor implements Interceptor {

    private static final String HEADER_API_KEY = "x-api-key";

    private final String apiKey;

    public InsertApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request request = originalRequest.newBuilder()
                .addHeader(HEADER_API_KEY, apiKey)
                .build();

        return chain.proceed(request);
    }

}
