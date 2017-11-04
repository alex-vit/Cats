package com.alexvit.cats.data.source.remote;

import com.alexvit.cats.data.model.api.Image;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRemoteDataSource {

    private final TheCatApiService service;

    public CatRemoteDataSource(TheCatApiService service) {
        this.service = service;
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return service.get(count)
                .map(response -> response.data.images);
    }
}
