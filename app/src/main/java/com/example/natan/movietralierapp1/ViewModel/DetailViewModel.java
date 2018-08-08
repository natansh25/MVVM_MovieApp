package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.natan.movietralierapp1.Respository;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;

import java.util.List;

public class DetailViewModel extends ViewModel {

    LiveData<List<MoviesResult>> mData;
    LiveData<List<ReviewResult>> mReviewData;

    private Respository mRespository;

    public DetailViewModel(int id) {
        mRespository = new Respository(id);
    }

    public void insert(MoviesResult result) {
        mRespository.insert(result);
    }

    public LiveData<List<MoviesResult>> getAllFav() {
        return mData;
    }

    public LiveData<List<ReviewResult>> getAllReviews() {
        mReviewData = mRespository.mReviewLiveData();
        return mReviewData;
    }


}
