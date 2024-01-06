package com.alexvit.cats.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alexvit.cats.Analytics;
import com.alexvit.cats.BaseViewModel;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.Image;

import java.util.List;

class ListViewModel extends BaseViewModel<ListState> {

    private final CatRepository catRepository;

    private ListViewModel(CatRepository catRepository) {
        super();
        this.catRepository = catRepository;
        loadImages();
    }

    @Override
    public void onError(Throwable throwable) {
        setState(new ListState(currentState.images(), false, true));
    }

    @Override
    protected ListState defaultState() {
        return new ListState(null, true, false);
    }

    void refresh() {
        subscribe(catRepository.fetchRandomImages(), this::onImages);
    }

    private void loadImages() {
        subscribe(catRepository.getRandomImages(), this::onImages);
    }

    private void onImages(List<Image> images) {
        setState(new ListState(images, false, false));
        Analytics.itemListView();
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
