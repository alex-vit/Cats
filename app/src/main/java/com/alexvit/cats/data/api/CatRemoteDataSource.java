package com.alexvit.cats.data.api;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.Image;
import com.alexvit.cats.di.scope.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@ApplicationScope
public class CatRemoteDataSource {

    public static final String BASE_URL = "https://api.thecatapi.com/v1/";

    private final TheCatApiService service;

    @Inject
    public CatRemoteDataSource(TheCatApiService service) {
        this.service = service;
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return service.getImages(count, CatRepository.SIZE_MEDIUM);
    }

    public Observable<Image> getImageById(String id) {
        return service.getImageById(id);
    }

}
