package com.alexvit.cats.data.source.remote;

import com.alexvit.cats.data.model.api.CatApiXmlResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public interface TheCatApiService {

    @GET("images/get?format=xml")
    Observable<CatApiXmlResponse> get(@Query("results_per_page") int resultsPerPage);

}
