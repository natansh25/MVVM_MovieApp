package com.example.natan.movietralierapp1.service;

import com.example.natan.movietralierapp1.model.Movies.Movies;
import com.example.natan.movietralierapp1.model.Reviews.Reviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    // query for movies

    @GET("movie/{filter}")
    Call<Movies> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey);


    // query for movie trailers

    @GET("movie/{id}/reviews")
    Call<Reviews> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);


}
