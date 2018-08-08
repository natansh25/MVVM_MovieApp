package com.example.natan.movietralierapp1.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final int movieId;

    public DetailViewModelFactory(int movieId) {
        this.movieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(movieId);
    }

}
