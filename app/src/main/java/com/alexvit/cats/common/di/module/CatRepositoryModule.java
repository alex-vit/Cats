package com.alexvit.cats.common.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexvit.cats.BuildConfig;
import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.data.api.CatRemoteDataSource;
import com.alexvit.cats.common.data.api.InsertApiKeyInterceptor;
import com.alexvit.cats.common.data.api.TheCatApiService;
import com.alexvit.cats.common.di.qualifier.ApiKey;
import com.alexvit.cats.common.di.qualifier.CacheFile;
import com.alexvit.cats.common.di.scope.ApplicationScope;

import java.io.File;

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
    CatRepository catRepository(CatRemoteDataSource remote, SharedPreferences preferences) {
        return new CatRepository(remote, preferences);
    }

    @Provides
    @ApplicationScope
    CatRemoteDataSource catRemoteDataSource(TheCatApiService service) {
        return new CatRemoteDataSource(service);
    }

    @Provides
    @ApplicationScope
    TheCatApiService theCatApiService(Retrofit tmdbRetrofit) {
        return tmdbRetrofit.create(TheCatApiService.class);
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(CatRemoteDataSource.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient okHttpClient(InsertApiKeyInterceptor insertApiKeyInterceptor, Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(insertApiKeyInterceptor).cache(cache);
        return builder.build();
    }

    @Provides
    @ApplicationScope
    InsertApiKeyInterceptor insertApiKeyInterceptor(@ApiKey String apiKey) {
        return new InsertApiKeyInterceptor(apiKey);
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
