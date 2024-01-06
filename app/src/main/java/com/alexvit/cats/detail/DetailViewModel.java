package com.alexvit.cats.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexvit.cats.BaseViewModel;
import com.alexvit.cats.data.CatRepository;

class DetailViewModel extends BaseViewModel<DetailState> {

    private final CatRepository repository;

    private DetailViewModel(CatRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    protected DetailState defaultState() {
        return new DetailState(null, true, false);
    }

    @Override
    public void onError(Throwable throwable) {
        setState(new DetailState(currentState.image(), false, true));
    }

    void load(String id) {
        subscribe(repository.getImageById(id), image -> setState(new DetailState(image, false, false)));
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
            return (T) new DetailViewModel(catRepository);
        }
    }
}
