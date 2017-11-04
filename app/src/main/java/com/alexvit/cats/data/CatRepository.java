package com.alexvit.cats.data;

import com.alexvit.cats.Constants;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.source.remote.CatRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class CatRepository {

    private final CatRemoteDataSource remote;

    public CatRepository(CatRemoteDataSource remote) {
        this.remote = remote;
    }

    public Observable<List<Image>> getRandomImages() {
        return getRandomImages(Constants.COUNT);
    }

    public Observable<List<Image>> getRandomImages(int count) {
        return remote.getRandomImages(count);
    }
}
