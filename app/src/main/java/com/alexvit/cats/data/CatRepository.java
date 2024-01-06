package com.alexvit.cats.data;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import com.alexvit.cats.data.api.CatRemoteDataSource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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
                .doOnNext(i -> images = i)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Image> getImageById(String id) {
        var cachedImage = getRandomImages()
                .filter(Objects::nonNull)
                .map(images -> {
                    Image found = null;
                    for (var image : images) {
                        if (image.id().equals(id)) {
                            found = image;
                        }
                    }
                    return found;
                })
                .filter(Objects::nonNull);
        return cachedImage
                .concatWith(remote.getImageById(id))
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io());
    }

    @StringDef({SIZE_SMALL, SIZE_MEDIUM, SIZE_FULL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Size {}

}
