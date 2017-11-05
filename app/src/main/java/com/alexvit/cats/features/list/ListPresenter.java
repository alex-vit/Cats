package com.alexvit.cats.features.list;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.util.Constants;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class ListPresenter extends BasePresenter<ListContract.View>
        implements ListContract.Presenter {

    private final CatRepository repository;

    public ListPresenter(CatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void attach(ListContract.View view) {
        super.attach(view);

        List<Image> cached = repository.getRandomImagesCache();
        if (cached != null) {
            // If possible, return something synchronously to preserve scroll position
            view.displayImages(cached);
        } else {
            loadRandomImages();
        }
    }

    @Override
    public void loadRandomImages() {
        Observable<List<Image>> observable = repository.getRandomImages(Constants.COUNT);
        subscribe(observable, view::displayImages);
    }

}
