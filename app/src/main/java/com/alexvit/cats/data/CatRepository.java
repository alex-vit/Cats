package com.alexvit.cats.data;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {

    private final CatRemoteDataSource remote;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private List<Image> randomImagesCache;

    public CatRepository(CatRemoteDataSource remote) {
        this.remote = remote;
    }

    public Observable<List<Image>> getRandomImages(int count) {

        return remote.getRandomImages(count)
                .doOnNext(list -> {
                    randomImagesCache = list;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public List<Image> getRandomImagesCache() {
        return randomImagesCache;
    }

    private <T> void subscribe(Observable<T> observable) {
        Disposable disposable = observable.subscribe();
        compositeDisposable.add(disposable);
    }
}
