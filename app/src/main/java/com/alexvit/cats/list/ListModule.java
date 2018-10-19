package com.alexvit.cats.list;

import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.data.CatRepository;

import dagger.Module;
import dagger.Provides;

@Module
class ListModule {

    @Provides
    @ActivityScope
    ListPresenter listPresenter(CatRepository repository) {
        return new ListPresenter(repository);
    }

//    @Provides
//    @ActivityScope
//    ListViewModel listViewModel(ListViewModel.Factory factory) {
//        return ViewModelProviders
//    }
//
//    @Provides
//    @ActivityScope
//    ListViewModel.Factory listViewModelFactory(CatRepository catRepository) {
//        return new ListViewModel.Factory(catRepository);
//    }

}
