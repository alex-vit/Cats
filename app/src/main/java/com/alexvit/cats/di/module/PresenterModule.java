package com.alexvit.cats.di.module;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.di.scope.ActivityScope;
import com.alexvit.cats.ui.detail.DetailPresenter;
import com.alexvit.cats.ui.list.ListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    ListPresenter listPresenter(CatRepository repository) {
        return new ListPresenter(repository);
    }

    @Provides
    @ActivityScope
    DetailPresenter detailPresenter(CatRepository repository) {
        return new DetailPresenter(repository);
    }

}
