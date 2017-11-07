package com.alexvit.cats.data;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.model.api.Vote;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;

import java.util.List;
import java.util.Map;

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
    private Map<String, Integer> votesCache;

    public CatRepository(CatRemoteDataSource remote) {
        this.remote = remote;
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return getRandomImages(count, false);
    }

    public Observable<List<Image>> getRandomImages(int count, boolean forceLoad) {

        final Observable<List<Image>> remoteObs = remote.getRandomImages(count)
                .doOnNext(list -> randomImagesCache = list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        if (forceLoad) {
            return remoteObs;
        } else {
            return fromNullable(randomImagesCache).onErrorResumeNext(remoteObs);
        }

    }

    public Observable<Image> getImageById(String id) {

        final Observable<Image> cacheObs = fromNullable(randomImagesCache)
                .flatMapIterable(v -> v)
                .filter(image -> image.id.equals(id))
                .firstOrError()
                .toObservable();

        final Observable<Image> remoteObs = remote.getImageById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return cacheObs.onErrorResumeNext(remoteObs);
    }

    public Observable<Map<String, Integer>> getVotes() {
        Observable<Map<String, Integer>> remoteObs = remote.getVotes()
                .doOnNext(map -> votesCache = map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return fromNullable(votesCache).onErrorResumeNext(remoteObs);
    }

    public Observable<Vote> vote(String id, int score) {
        return remote.vote(id, score)
                // invalidate as soon as we attempt to vote, not in onNext
                .doOnSubscribe(__ -> votesCache = null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T> Observable<T> fromNullable(T nullable) {
        return Observable.fromCallable(() -> {
            if (nullable == null) {
                throw new NullPointerException("Item is null");
            } else {
                return nullable;
            }
        });
    }
}
