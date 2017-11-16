package com.alexvit.cats.data.source.remote;

import com.alexvit.cats.data.model.Image;
import com.alexvit.cats.data.model.Vote;

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

    public Observable<List<Image>> getRandomImages(int count, String subId) {
        return service.getImages(count, Contract.SIZE_FULL, subId)
                .map(response -> response.data.images);
    }

    public Observable<Image> getImageById(String id, String subId) {
        return service.getImageById(id, Contract.SIZE_FULL, subId)
                .map(response -> response.data.images.get(0));
    }

    public Observable<Vote> vote(String id, int score, String subId) {
        return service.vote(id, score, subId)
                .map(response -> response.data.votes.get(0));
    }
}
