package com.alexvit.cats.data.source.remote;

import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.model.api.Vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Observable<Map<String, Integer>> getVotes() {
        return service.getVotes()
                .map(response -> {
                    ArrayList<Image> images = response.data.images;
                    Map<String, Integer> map = new HashMap<>();
                    for (Image image : images) {
                        String id = image.id;
                        int score = image.score;
                        map.put(id, score);
                    }
                    return map;
                });
    }

    public Observable<Vote> vote(String id, int score, String subId) {
        return service.vote(id, score, subId)
                .map(response -> response.data.votes.get(0));
    }
}
