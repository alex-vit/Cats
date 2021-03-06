package com.alexvit.cats.common.data.api;

import com.alexvit.cats.common.data.CatRepository;
import com.alexvit.cats.common.data.Image;

import java.util.List;

import androidx.annotation.IntRange;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public interface TheCatApiService {

    @GET("images/search?mime_types=jpg,png&order=RANDOM")
    Observable<List<Image>> getImages(
            @Query("limit") @IntRange(from = 1) Integer limit,
            @Query("size") @CatRepository.Size String size
    );

    @GET("images/{id}")
    Observable<Image> getImageById(@Path("id") String id);

}
