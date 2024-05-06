/*
 * Copyright (c) 2024 Aleksandrs Vitjukovs. All rights reserved.
 */

package com.alexvit.cats.data;

import com.alexvit.cats.data.api.TheCatApiService;
import com.alexvit.cats.di.scope.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

@ApplicationScope
public class CatRepository {

    private final TheCatApiService api;
    private final BehaviorSubject<List<Image>> imagesSubject = BehaviorSubject.create();
    private final Flowable<List<Image>> sharedImagesFlowable;

    @Inject
    public CatRepository(TheCatApiService api) {
        this.api = api;
        this.sharedImagesFlowable = Single.fromCallable(imagesSubject::hasValue)
                .toFlowable()
                .flatMap(hasValue -> hasValue
                        ? imagesSubject.toFlowable(BackpressureStrategy.LATEST)
                        : fetchRandomImages().toFlowable(BackpressureStrategy.LATEST))
                .share();
    }

    public Observable<List<Image>> getRandomImages() {
        return sharedImagesFlowable.toObservable();
    }

    public Observable<List<Image>> fetchRandomImages() {
        return api.getImages(20)
                .toObservable()
                .doOnNext(imagesSubject::onNext)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Image> findImage(String id) {
        return getRandomImages().flatMap(images -> {
            for (var img : images) {
                if (img.id().equals(id)) {
                    return Observable.just(img);
                }
            }
            return Observable.error(new IllegalStateException(String.format("Image \"%s\" not found.", id)));
        });
    }

}
