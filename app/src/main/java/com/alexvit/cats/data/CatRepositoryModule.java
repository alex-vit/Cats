package com.alexvit.cats.data;

import android.content.Context;

import com.alexvit.cats.BuildConfig;
import com.alexvit.cats.data.api.InsertApiKeyInterceptor;
import com.alexvit.cats.data.api.TheCatApiService;
import com.alexvit.cats.di.qualifier.ApiKey;
import com.alexvit.cats.di.qualifier.CacheFile;
import com.alexvit.cats.di.scope.ApplicationScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class CatRepositoryModule {

    @Provides
    @ApplicationScope
    TheCatApiService theCatApiService(Retrofit tmdbRetrofit) {
        return tmdbRetrofit.create(TheCatApiService.class);
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(TheCatApiService.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient okHttpClient(InsertApiKeyInterceptor insertApiKeyInterceptor, Cache cache) {
        int timeout = 5_000;
        return new OkHttpClient.Builder()
                .callTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(insertApiKeyInterceptor)
                .build();
    }

    @Provides
    @ApplicationScope
    Cache cache(@CacheFile File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024);
    }

    @Provides
    @ApplicationScope
    @CacheFile
    File cacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp-cache");
    }

    @Provides
    @ApplicationScope
    @ApiKey
    String apiKey() {
        return BuildConfig.THE_CAT_API_KEY;
    }

}
