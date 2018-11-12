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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteNetworkCall {

    private static MutableLiveData<List<MoviesResult>> data = new MutableLiveData<>();
    private static MutableLiveData<List<ReviewResult>> dataReviews = new MutableLiveData<>();
    private static MutableLiveData<List<TrailerResult>> dataTrailer = new MutableLiveData<>();


    private static Observable<Movies> mMoviesObservable;
    private static Observable<Reviews> mReviewsObservable;
    private static Observable<Trailers> mTrailersObservable;
    private static CompositeDisposable com = new CompositeDisposable();


    //-------fetching data for movie details popular / H-rated------------------------------------


    public static void fetchData(String sort) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        mMoviesObservable = apiService.getMovies(sort, ApiClient.api_key);
        com.add(mMoviesObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Movies>() {
                    @Override
                    public void onNext(Movies movies) {
                        List<MoviesResult> results = movies.getResults();
                        data.postValue(results);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
  /*      call.enqueue(new Callback<Movies>() {
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


        });*/


    }


    //-------fetching data for movie reviews-------------------------------------------------------

    public static void fetchMovieReview(int id) {


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        mReviewsObservable = apiService.getMovieReviews(id, ApiClient.api_key);
        com.add(mReviewsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Reviews>() {
                    @Override
                    public void onNext(Reviews reviews) {

                        List<ReviewResult> results = reviews.getResults();


                        dataReviews.postValue(results);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    // fetching data for movie trailers-----------------------------------------------------------


    public static void fetchMovieTrailer(int id) {




        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        mTrailersObservable = apiService.getMovieTrailers(id, ApiClient.api_key);
       mTrailersObservable.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableObserver<Trailers>() {
                   @Override
                   public void onNext(Trailers trailers) {
                       List<TrailerResult> results = trailers.getResults();


                       dataTrailer.postValue(results);
                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {

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