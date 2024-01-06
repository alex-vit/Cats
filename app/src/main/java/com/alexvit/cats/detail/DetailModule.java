package com.alexvit.cats.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
class DetailModule {

    @Provides
    @ActivityScope
    DetailViewModel detailViewModel(AppCompatActivity activity, DetailViewModel.Factory factory) {
        return new ViewModelProvider(activity.getViewModelStore(), factory).get(DetailViewModel.class);
    }

    @Provides
    @ActivityScope
    DetailViewModel.Factory detailViewModelFactory(CatRepository catRepository) {
        return new DetailViewModel.Factory(catRepository);
    }

}
