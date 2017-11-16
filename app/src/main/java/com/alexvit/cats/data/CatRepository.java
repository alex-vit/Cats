package com.alexvit.cats.data;

import android.content.SharedPreferences;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.model.api.Vote;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;
import com.alexvit.cats.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {

    private final CatRemoteDataSource remote;
    private final SharedPreferences preferences;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    // TODO Make this a Set / Map. Get rid of votes cache
    private List<Image> randomImagesCache;
    private Map<String, Integer> votesCache;
    private String userId;

    public CatRepository(CatRemoteDataSource remote, SharedPreferences preferences) {
        this.remote = remote;
        this.preferences = preferences;

        userId = getUserId(preferences);
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return getRandomImages(count, false);
    }

    public Observable<List<Image>> getRandomImages(int count, boolean forceLoad) {

        final Observable<List<Image>> remoteObs = remote.getRandomImages(count, userId)
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

        final Observable<Image> remoteObs = remote.getImageById(id, userId)
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
        return remote.vote(id, score, userId)
                // invalidate as soon as we attempt to vote, not in onNext
//                .doOnSubscribe(__ -> votesCache = null)
                .doOnComplete(() -> updateScoreInCache(randomImagesCache, id, score))
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

    private static void updateScoreInCache(List<Image> cache, String imageId, int score) {
        for (Image image : cache) {
            if (image.id.equals(imageId)) {
                image.score = score;
            }
        }
    }

    private static String getUserId(SharedPreferences preferences) {
        String userId = preferences.getString(Constants.PREF_USER_ID, null);
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            saveUserId(preferences, userId);
        }
        return userId;
    }

    private static void saveUserId(SharedPreferences preferences, String userId) {
        preferences.edit()
                .putString(Constants.PREF_USER_ID, userId)
                .apply();
    }

}
