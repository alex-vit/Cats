package com.alexvit.cats.common.data.api;

import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.data.Image;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRemoteDataSource {

    public static final String BASE_URL = "https://api.thecatapi.com/v1/";

    private final TheCatApiService service;

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
