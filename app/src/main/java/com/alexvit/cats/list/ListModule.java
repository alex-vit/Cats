package com.alexvit.cats.list;

import android.arch.lifecycle.ViewModelProviders;

import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.data.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
class ListModule {

    private final ListActivity activity;

    ListModule(ListActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    ListPresenter listPresenter(CatRepository repository) {
        return new ListPresenter(repository);
    }

    @Provides
    @ActivityScope
    ListViewModel listViewModel(ListActivity activity, ListViewModel.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(ListViewModel.class);
    }

    @Provides
    @ActivityScope
    ListViewModel.Factory listViewModelFactory(CatRepository catRepository) {
        return new ListViewModel.Factory(catRepository);
    }

    @Provides
    @ActivityScope
    ListActivity listActivity() {
        return activity;
    }

}
