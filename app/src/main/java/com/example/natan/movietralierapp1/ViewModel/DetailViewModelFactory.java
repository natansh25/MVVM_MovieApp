package com.example.natan.movietralierapp1.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final int movieId;
    Context mContext;



    public DetailViewModelFactory(int movieId, Context context) {
        this.movieId = movieId;
        mContext = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(movieId,mContext);
    }

}
