package com.alexvit.cats.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
class ListModule {

    @Provides
    @ActivityScope
    ListViewModel listViewModel(AppCompatActivity activity, ListViewModel.Factory factory) {
        return new ViewModelProvider(activity.getViewModelStore(), factory).get(ListViewModel.class);
    }

    @Provides
    @ActivityScope
    ListViewModel.Factory listViewModelFactory(CatRepository catRepository) {
        return new ListViewModel.Factory(catRepository);
    }

}
