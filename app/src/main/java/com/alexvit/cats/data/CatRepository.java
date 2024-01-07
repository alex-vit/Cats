package com.alexvit.cats.data;

import androidx.annotation.Nullable;

import com.alexvit.cats.data.api.CatRemoteDataSource;
import com.alexvit.cats.di.scope.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@ApplicationScope
public class CatRepository {
    private static final int DEFAULT_IMAGE_COUNT = 20;

    private final CatRemoteDataSource remote;
    @Nullable
    private List<Image> images = null;

    @Inject
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

    public Observable<Image> getImage(String id) {
        return getRandomImages()
                .flatMap(images -> {
                    if (images != null) {
                        for (var img : images) {
                            if (img.id().equals(id)) {
                                return Observable.just(img);
                            }
                        }
                    }
                    return Observable.error(new IllegalStateException(String.format("Image \"%s\" not found.", id)));
                });
    }

}
