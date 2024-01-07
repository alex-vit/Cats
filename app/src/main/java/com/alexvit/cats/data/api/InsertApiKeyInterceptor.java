package com.alexvit.cats.data.api;

import androidx.annotation.NonNull;

import com.alexvit.cats.di.qualifier.ApiKey;
import com.alexvit.cats.di.scope.ApplicationScope;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;

@ApplicationScope
public class InsertApiKeyInterceptor implements Interceptor {

    private static final String HEADER_API_KEY = "x-api-key";

    private final String apiKey;

    @Inject
    public InsertApiKeyInterceptor(@ApiKey String apiKey) {
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
