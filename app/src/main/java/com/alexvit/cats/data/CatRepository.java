package com.alexvit.cats.data;

import static com.alexvit.cats.Transformers.schedulers;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import com.alexvit.cats.data.api.CatRemoteDataSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {
    public static final String SIZE_SMALL = "small";
    public static final String SIZE_MEDIUM = "med";
    public static final String SIZE_FULL = "full";
    private static final int DEFAULT_IMAGE_COUNT = 20;

    private final CatRemoteDataSource remote;
    @Nullable
    private List<Image> images = null;

    public CatRepository(CatRemoteDataSource remote) {
        this.remote = remote;
    }

    public Observable<List<Image>> getRandomImages() {
        if (images != null) {
            return Observable.just(images);
        } else {
            return fetchRandomImages();
        }
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

}
