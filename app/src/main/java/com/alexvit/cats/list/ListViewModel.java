package com.alexvit.cats.list;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alexvit.cats.Analytics;
import com.alexvit.cats.common.base.BaseViewModel;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.Image;

import java.util.List;

class ListViewModel extends BaseViewModel<ListViewModel.State> {

    private final CatRepository catRepository;

    private ListViewModel(Lifecycle lifecycle, CatRepository catRepository) {
        super(lifecycle);
        this.catRepository = catRepository;
    }

    @Override
    protected void onStart() {
        loadImages();
    }

    @Override
    public void onError(Throwable throwable) {
        setState(State.error());
    }

    @Override
    protected State defaultState() {
        return State.loading();
    }

    void refresh() {
        subscribe(catRepository.fetchRandomImages(), this::onImages);
    }

    private void loadImages() {
        subscribe(catRepository.getRandomImages(), this::onImages);
    }

    private void onImages(List<Image> images) {
        setState(State.images(images));
        Analytics.itemListView();
    }

    enum Error {
        UNKNOWN
    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Lifecycle lifecycle;
        private final CatRepository catRepository;

        Factory(Lifecycle lifecycle, CatRepository catRepository) {
            this.lifecycle = lifecycle;
            this.catRepository = catRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ListViewModel(lifecycle, catRepository);
        }
    }

    static class State {

        final boolean loading;
        @Nullable
        final List<Image> images;
        @Nullable
        final Error error;

        State(boolean loading, @Nullable List<Image> images, @Nullable Error error) {
            this.loading = loading;
            this.images = images;
            this.error = error;
        }

        static State loading() {
            return new State(true, null, null);
        }

        static State images(List<Image> images) {
            return new State(false, images, null);
        }

        static State error() {
            return new State(false, null, Error.UNKNOWN);
        }
    }

}
