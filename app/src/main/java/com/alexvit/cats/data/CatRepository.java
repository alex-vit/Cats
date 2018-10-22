package com.alexvit.cats.data;

import android.content.SharedPreferences;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.alexvit.cats.common.rx.Observables;
import com.alexvit.cats.data.model.Image;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;
import com.alexvit.cats.util.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;

import static com.alexvit.cats.common.rx.Transformers.schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {

    @StringDef({SIZE_SMALL, SIZE_MEDIUM, SIZE_FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {
    }

    public static final String SIZE_SMALL = "small";
    public static final String SIZE_MEDIUM = "med";
    public static final String SIZE_FULL = "full";

    @IntDef({SCORE_LOVE, SCORE_HATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Score {
    }

    public static final int SCORE_LOVE = 10;
    public static final int SCORE_HATE = 1;

    private static final int DEFAULT_IMAGE_COUNT = 20;

    private final CatRemoteDataSource remote;

    private final String userId;

    private List<Image> images = null;

    public CatRepository(CatRemoteDataSource remote, SharedPreferences preferences) {
        this.remote = remote;

        userId = getUserId(preferences);
    }

    public Observable<List<Image>> getRandomImages() {
        return Observables.fromNullable(images).onErrorResumeNext(fetchRandomImages());
    }

    public Observable<List<Image>> fetchRandomImages() {
        return remote.getRandomImages(DEFAULT_IMAGE_COUNT, userId)
                .compose(schedulers())
                .doOnNext(i -> images = i);
    }

    public Observable<Image> getImageById(String id) {
        return remote.getImageById(id, userId).compose(schedulers());
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
