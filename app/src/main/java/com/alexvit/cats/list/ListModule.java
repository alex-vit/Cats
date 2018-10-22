package com.alexvit.cats.list;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.data.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
class ListModule {

    @Provides
    @ActivityScope
    ListViewModel listViewModel(AppCompatActivity activity, ListViewModel.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(ListViewModel.class);
    }

    @Provides
    @ActivityScope
    ListViewModel.Factory listViewModelFactory(Lifecycle lifecycle, CatRepository catRepository) {
        return new ListViewModel.Factory(lifecycle, catRepository);
    }

}
