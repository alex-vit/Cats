package com.alexvit.cats.features.list;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.api.Image;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void loadRandomImages() {

        Observable<List<Image>> observable = repository.getRandomImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscribe(observable,
                getView()::displayImages,
                t -> {
                },
                () -> {
                });
    }

}
