package com.alexvit.cats.di.module;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;
import com.alexvit.cats.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module(includes = {CatRemoteDataSourceModule.class})
public class CatRepositoryModule {

    @Provides
    @ApplicationScope
    CatRepository catRepository(CatRemoteDataSource remote) {
        return new CatRepository(remote);
    }

}
