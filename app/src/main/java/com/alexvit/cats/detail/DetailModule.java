package com.alexvit.cats.detail;

import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.di.scope.ActivityScope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
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
