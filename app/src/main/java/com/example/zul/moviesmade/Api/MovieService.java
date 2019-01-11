package com.example.zul.moviesmade.Api;

import com.example.zul.moviesmade.Model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("search/movie")
    Call<Response>
    getSearchedMovie(@Query("api_key") String apiKey,
                     @Query("language") String language,
                     @Query("query") String query
    );

}
