package com.alexvit.cats.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.alexvit.cats.data.CatRepository;

class ListViewModel extends ViewModel {

    private final CatRepository catRepository;

    ListViewModel(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final CatRepository catRepository;

        Factory(CatRepository catRepository) {
            this.catRepository = catRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ListViewModel(catRepository);
        }
    }

}
