package com.alexvit.cats.detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.data.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
class DetailModule {

    @Provides
    @ActivityScope
    DetailViewModel detailViewModel(AppCompatActivity activity, DetailViewModel.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(DetailViewModel.class);
    }

    @Provides
    @ActivityScope
    DetailViewModel.Factory detailViewModelFactory(Lifecycle lifecycle, CatRepository catRepository) {
        return new DetailViewModel.Factory(lifecycle, catRepository);
    }

}
