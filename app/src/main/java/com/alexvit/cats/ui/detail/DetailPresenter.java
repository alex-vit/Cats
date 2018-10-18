package com.alexvit.cats.ui.detail;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.Vote;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View>
        implements DetailContract.Presenter {

    private final CatRepository repository;

    public DetailPresenter(CatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setId(String id) {
        subscribe(repository.getImageById(id), view::displayImage);
    }

}
