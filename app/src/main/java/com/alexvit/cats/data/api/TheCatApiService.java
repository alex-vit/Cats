/*
 * Copyright (c) 2024 Aleksandrs Vitjukovs. All rights reserved.
 */

package com.alexvit.cats.data.api;

import androidx.annotation.IntRange;

import com.alexvit.cats.data.Image;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TheCatApiService {

    String BASE_URL = "https://api.thecatapi.com/v1/";

    @GET("images/search?mime_types=jpg,png&order=RANDOM")
    Single<List<Image>> getImages(@Query("limit") @IntRange(from = 1) int limit);

}
