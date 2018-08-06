package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.natan.movietralierapp1.Respository;
import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Result>> mData;
    private LiveData<List<Result>> mDataFav;
    private Respository mRespository;

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("xxx","data cleared");
    }

    public MainViewModel(@NonNull Application application) {
        super(application);


        mRespository = new Respository(application);

    }

    public LiveData<List<Result>> mLiveData() {
        mData = mRespository.mLiveData();
        return mData;
    }

    public LiveData<List<Result>> mLiveDataFav() {
        if (mDataFav == null) {
            mDataFav = new MutableLiveData<>();
        }
        mDataFav = mRespository.mLiveDataFav();
        return mDataFav;
    }


    public void getTopRated() {
        mRespository.getTopRated();
    }

    public void getPopular() {
        mRespository.getPopular();
    }

    public void getFavData() {
        mRespository.getFavData();


    }
}
