package com.alexvit.cats.data.source.remote;

import android.support.annotation.IntRange;

import com.alexvit.cats.data.model.api.ImageXmlResponse;
import com.alexvit.cats.data.model.api.VoteXmlResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public interface TheCatApiService {

    @GET("images/get?format=xml&type=jpg,png")
    Observable<ImageXmlResponse> getImages(
            @Query("results_per_page") @IntRange(from = 1) Integer resultsPerPage,
            @Query("size") @Contract.Size String size,
            @Query("sub_id") String subId
    );

    @GET("images/get?format=xml&type=jpg,png")
    Observable<ImageXmlResponse> getImageById(
            @Query("image_id") String imageId,
            @Query("size") @Contract.Size String size,
            @Query("sub_id") String subId
    );

    @GET("images/getvotes")
    Observable<ImageXmlResponse> getVotes();

    @GET("images/vote")
    Observable<VoteXmlResponse> vote(
            @Query("image_id") String imageId,
            @Query("score") @Contract.Score int score,
            @Query("sub_id") String subId
    );

}
