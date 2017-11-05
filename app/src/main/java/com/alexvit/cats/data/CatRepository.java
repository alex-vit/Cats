package com.alexvit.cats.data;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
        return getRandomImages(count, false);
    }

    public Observable<List<Image>> getRandomImages(int count, boolean forceLoad) {

        final Observable<List<Image>> remoteObs = remote.getRandomImages(count)
                .doOnNext(list -> {
                    randomImagesCache = list;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        if (forceLoad) {
            randomImagesCache = null;
            return remoteObs;
        } else {
            return listObservable(randomImagesCache).onErrorResumeNext(remoteObs);
        }

    }

    public Observable<Image> getImageById(String id) {
        return remote.getImageById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Image>> listObservable(List<Image> list) {
        return Observable.fromCallable(() -> {
            if (randomImagesCache == null) {
                throw new NullPointerException("List is null");
            } else {
                return randomImagesCache;
            }
        });
    }
}
