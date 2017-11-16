package com.alexvit.cats.data;

import android.content.SharedPreferences;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.model.api.Vote;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;
import com.alexvit.cats.util.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {

    private final CatRemoteDataSource remote;

    private Map<String, Image> randomImagesCache = new LinkedHashMap<>();
    private String userId;

    public CatRepository(CatRemoteDataSource remote, SharedPreferences preferences) {
        this.remote = remote;

        userId = getUserId(preferences);
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return getRandomImages(count, false);
    }

    public Observable<List<Image>> getRandomImages(int count, boolean forceLoad) {

        final Observable<List<Image>> remoteObs = remote.getRandomImages(count, userId)
                .doOnNext(this::cacheImageList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        if (forceLoad || randomImagesCache.isEmpty()) {
            return remoteObs;
        } else {
            final List<Image> imageList = new ArrayList<>(randomImagesCache.values());
            return Observable.just(imageList);
        }

    }

    public Observable<Image> getImageById(String id) {

        final Image image = randomImagesCache.get(id);
        if (image != null) {
            return Observable.just(image);
        } else {
            return remote.getImageById(id, userId)
                    .doOnNext(this::cacheImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }

    public Observable<Vote> vote(String id, int score) {
        return remote.vote(id, score, userId)
                .doOnComplete(() -> updateScoreInCache(id, score))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void updateScoreInCache(String imageId, int score) {
        final Image image = randomImagesCache.get(imageId);
        image.score = score;
        randomImagesCache.put(imageId, image);
    }

    private void cacheImage(Image image) {
        randomImagesCache.put(image.id, image);
    }

    private void cacheImageList(List<Image> imageList) {
        randomImagesCache.clear();
        for (Image image : imageList) {
            cacheImage(image);
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
