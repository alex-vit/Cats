package com.alexvit.cats.di.module;

import android.content.Context;

import com.alexvit.cats.BuildConfig;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;
import com.alexvit.cats.data.source.remote.InsertApiKeyInterceptor;
import com.alexvit.cats.data.source.remote.TheCatApiService;
import com.alexvit.cats.di.qualifier.ApiKey;
import com.alexvit.cats.di.qualifier.ApplicationContext;
import com.alexvit.cats.di.qualifier.CacheFile;
import com.alexvit.cats.di.scope.ApplicationScope;
import com.alexvit.cats.util.Constants;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class CatRemoteDataSourceModule {

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
                .baseUrl(Constants.CAT_API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
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
    File cacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp-cache");
    }

    @Provides
    @ApplicationScope
    @ApiKey
    String apiKey() {
        return BuildConfig.THE_CAT_API_KEY;
    }

}
