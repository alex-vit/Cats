package com.alexvit.cats.list;

import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.di.scope.ActivityScope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
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
