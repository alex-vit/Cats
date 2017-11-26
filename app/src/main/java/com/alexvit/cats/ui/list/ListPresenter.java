package com.alexvit.cats.ui.list;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.Image;
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

        loadRandomImages(false);
    }

    @Override
    public void loadRandomImages(boolean refresh) {

        final List<Image> cachedImages = repository.getCachedRandomImages();

        if (refresh || cachedImages.isEmpty()) {
            view.showLoading(true);
            Observable<List<Image>> observable = repository.getRandomImages(Constants.COUNT);
            subscribe(observable, images -> {
                view.displayImages(images);
                view.logViewItemList();
            });
        } else {
            view.displayImages(cachedImages);
        }

    }

}
