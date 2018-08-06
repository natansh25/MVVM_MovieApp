package com.example.natan.movietralierapp1.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;


import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteNetworkCall {

    private static MutableLiveData<List<Result>> data = new MutableLiveData<>();


    public static void fetchData(String sort) {
        Log.d("RemoteSort",sort);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiService.getMovies(sort, ApiClient.api_key);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, final Response<Example> response) {
                int statusCode = response.code();
                List<Result> results = response.body().getResults();


                data.postValue(results);

                //Log.d("RemoteIN", String.valueOf(data));


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }


        });
        Log.d("RemoteOUT", String.valueOf(data));


    }



    public static LiveData<List<Result>> getIntData() {
        return data;
    }


}