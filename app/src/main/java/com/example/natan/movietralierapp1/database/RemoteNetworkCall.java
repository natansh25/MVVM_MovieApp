package com.example.natan.movietralierapp1.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;


import com.example.natan.movietralierapp1.model.Movies.Movies;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteNetworkCall {

    private static MutableLiveData<List<MoviesResult>> data = new MutableLiveData<>();


    public static void fetchData(String sort) {
        Log.d("RemoteSort",sort);


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



    public static LiveData<List<MoviesResult>> getIntData() {
        return data;
    }


}