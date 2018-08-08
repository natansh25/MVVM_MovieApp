package com.example.natan.movietralierapp1.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.util.Log;


import com.example.natan.movietralierapp1.model.Movies.Movies;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;
import com.example.natan.movietralierapp1.model.Reviews.Reviews;
import com.example.natan.movietralierapp1.model.Trailer.TrailerResult;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class RemoteNetworkCall {

    private static MutableLiveData<List<MoviesResult>> data = new MutableLiveData<>();
    private static MutableLiveData<List<ReviewResult>> dataReviews = new MutableLiveData<>();
    private static MutableLiveData<List<TrailerResult>> dataTrailer = new MutableLiveData<>();


    //-------fetching data for movie details popular / H-rated
    public static void fetchData(String sort) {
        Log.d("RemoteSort", sort);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movies> call = apiService.getMovies(sort, ApiClient.api_key);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, final Response<Movies> response) {
                int statusCode = response.code();
                List<MoviesResult> results = response.body().getResults();


                data.postValue(results);

                //Log.d("RemoteIN", String.valueOf(data));


            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }


        });
        Log.d("RemoteOUT", String.valueOf(data));


    }


    //-------fetching data for movie reviews

    public static void fetchMovieReview(int id) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Reviews> call = apiService.getMovieReviews(id, ApiClient.api_key);
        String url=call.request().url().toString();
        Log.d("uuuReviews", url);

        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, final Response<Reviews> response) {
                int statusCode = response.code();
                List<ReviewResult> results = response.body().getResults();


                dataReviews.postValue(results);


                Log.d("yyy", String.valueOf(results));


            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

                Log.d("uuuFailer", String.valueOf(call));

            }


        });
        Log.d("RemoteOUT", String.valueOf(data));


    }


    public static LiveData<List<MoviesResult>> getIntData() {
        return data;
    }

    public static LiveData<List<ReviewResult>> getReviewsData() {
        return dataReviews;
    }


}