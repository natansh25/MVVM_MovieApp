package com.example.natan.movietralierapp1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.natan.movietralierapp1.RoomDatabase.AppDatabase;
import com.example.natan.movietralierapp1.RoomDatabase.MovieDao;
import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;

import java.util.List;

public class Respository {

    private MovieDao mMovieDao;

    private LiveData<List<MoviesResult>> mData;
    private LiveData<List<MoviesResult>> mDataFav;


    public Respository(Application application) {

        AppDatabase db = AppDatabase.getDatabase(application);
        mMovieDao = db.movieDao();

        RemoteNetworkCall.fetchData("popular");


    }

    public LiveData<List<MoviesResult>> mLiveData() {
        mData = RemoteNetworkCall.getIntData();

        return mData;
    }

    public void getFavData() {
        mDataFav = mMovieDao.getAllFav();

    }

    public LiveData<List<MoviesResult>> mLiveDataFav() {

        return mDataFav;
    }

    public void getTopRated() {
        RemoteNetworkCall.fetchData("top_rated");
    }

    public void getPopular() {
        RemoteNetworkCall.fetchData("popular");
    }


    //----------------------------------------------------------------------------------


    public void insert(MoviesResult result) {

        new insertAsyncTask(mMovieDao).execute(result);
    }


    class insertAsyncTask extends AsyncTask<MoviesResult, Void, Void> {

        private MovieDao mMovieDao;

        public insertAsyncTask(MovieDao movieDao) {

            mMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MoviesResult... results) {

            mMovieDao.insert(results[0]);

            return null;
        }
    }

}
