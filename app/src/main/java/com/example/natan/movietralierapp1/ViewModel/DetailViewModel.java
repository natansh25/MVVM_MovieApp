package com.example.natan.movietralierapp1.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.natan.movietralierapp1.Respository;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;
import com.example.natan.movietralierapp1.model.Trailer.TrailerResult;

import java.util.List;

public class DetailViewModel extends ViewModel {

    LiveData<List<MoviesResult>> mData;
    LiveData<List<ReviewResult>> mReviewData;
    LiveData<List<TrailerResult>> mTrailerData;

    private Respository mRespository;

    public DetailViewModel(int id, Context context) {
        mRespository = new Respository(id, context);

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

    public LiveData<List<TrailerResult>> getAllTrailers() {
        mTrailerData = mRespository.mTrailerLiveData();
        return mTrailerData;
    }


}
