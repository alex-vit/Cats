package com.alexvit.cats.common.data;

import android.content.SharedPreferences;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.alexvit.cats.common.data.api.CatRemoteDataSource;
import com.alexvit.cats.common.rx.Observables;

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

    private static final String PREF_USER_ID = "user-id";

    public static final String SIZE_SMALL = "small";
    public static final String SIZE_MEDIUM = "med";
    public static final String SIZE_FULL = "full";

    private static String getUserId(SharedPreferences preferences) {
        String userId = preferences.getString(PREF_USER_ID, null);
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            saveUserId(preferences, userId);
        }
        return userId;
    }

    public static final int SCORE_LOVE = 10;
    public static final int SCORE_HATE = 1;

    private static void saveUserId(SharedPreferences preferences, String userId) {
        preferences.edit()
                .putString(PREF_USER_ID, userId)
                .apply();
    }
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
        return remote.getRandomImages(DEFAULT_IMAGE_COUNT)
                .compose(schedulers())
                .doOnNext(i -> images = i);
    }

    public Observable<Image> getImageById(String id) {
        return remote.getImageById(id).compose(schedulers());
    }

    @StringDef({SIZE_SMALL, SIZE_MEDIUM, SIZE_FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {}

    @IntDef({SCORE_LOVE, SCORE_HATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Score {}

}
