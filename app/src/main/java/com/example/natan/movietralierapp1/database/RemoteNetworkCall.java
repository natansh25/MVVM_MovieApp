package com.example.natan.movietralierapp1.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import com.example.natan.movietralierapp1.model.Movies.Movies;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;
import com.example.natan.movietralierapp1.model.Reviews.Reviews;
import com.example.natan.movietralierapp1.model.Trailer.TrailerResult;
import com.example.natan.movietralierapp1.model.Trailer.Trailers;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteNetworkCall {

    private static MutableLiveData<List<MoviesResult>> data = new MutableLiveData<>();
    private static MutableLiveData<List<ReviewResult>> dataReviews = new MutableLiveData<>();
    private static MutableLiveData<List<TrailerResult>> dataTrailer = new MutableLiveData<>();


    //-------fetching data for movie details popular / H-rated------------------------------------


    public static void fetchData(String sort) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movies> call = apiService.getMovies(sort, ApiClient.api_key);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, final Response<Movies> response) {
                int statusCode = response.code();
                List<MoviesResult> results = response.body().getResults();


                data.postValue(results);

                //


            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }


        });


    }


    //-------fetching data for movie reviews-------------------------------------------------------

    public static void fetchMovieReview(int id) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Reviews> call = apiService.getMovieReviews(id, ApiClient.api_key);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, final Response<Reviews> response) {
                int statusCode = response.code();
                List<ReviewResult> results = response.body().getResults();


                dataReviews.postValue(results);


            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }


        });


    }


    // fetching data for movie trailers-----------------------------------------------------------


    public static void fetchMovieTrailer(int id) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Trailers> call = apiService.getMovieTrailers(id, ApiClient.api_key);
        call.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, final Response<Trailers> response) {
                int statusCode = response.code();
                List<TrailerResult> results = response.body().getResults();


                dataTrailer.postValue(results);


            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {

            }


        });


    }


    public static LiveData<List<MoviesResult>> getIntData() {
        return data;
    }

    public static LiveData<List<ReviewResult>> getReviewsData() {
        return dataReviews;
    }

    public static LiveData<List<TrailerResult>> getTrailerData() {
        return dataTrailer;
    }


}