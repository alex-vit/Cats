package com.alexvit.cats.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexvit.cats.common.base.BaseViewModel;
import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.data.Image;

class DetailViewModel extends BaseViewModel<DetailViewModel.State> {

    private final CatRepository repository;

    private DetailViewModel(Lifecycle lifecycle, CatRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    protected State defaultState() {
        return State.loading();
    }

    @Override
    public void onError(Throwable throwable) {
        setState(State.error(Error.UNKNOWN));
    }

    void load(String id) {
        subscribe(repository.getImageById(id), image -> setState(State.image(image)));
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
            return (T) new DetailViewModel(lifecycle, catRepository);
        }
    }

    // TODO records?
    static class State {

        @Nullable
        final Image image;
        @Nullable
        final Error error;

        private State(@Nullable Image image, @Nullable Error error) {
            this.image = image;
            this.error = error;
        }

        static State loading() {
            return new State(null, null);
        }

        static State image(Image image) {
            return new State(image, null);
        }

        static State error(Error error) {
            return new State(null, error);
        }

    }
}
