package com.alexvit.cats.detail;

import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.data.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
class DetailModule {

    @Provides
    @ActivityScope
    DetailPresenter detailPresenter(CatRepository catRepository) {
        return new DetailPresenter(catRepository);
    }

}
