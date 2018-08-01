package com.example.natan.movietralierapp1;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.natan.movietralierapp1.Adapter.Movie;
import com.example.natan.movietralierapp1.Adapter.RecyclerMovie;
import com.example.natan.movietralierapp1.Network.NetworkUtils;
import com.example.natan.movietralierapp1.ViewModel.MainViewModel;
import com.example.natan.movietralierapp1.database.RemoteNetworkCall;
import com.example.natan.movietralierapp1.model.Example;
import com.example.natan.movietralierapp1.model.Result;
import com.example.natan.movietralierapp1.service.ApiClient;
import com.example.natan.movietralierapp1.service.ApiInterface;
import com.facebook.stetho.Stetho;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    private RecyclerMovie mRecyclerMovie;
    MainViewModel viewModel;

    // onSaveinstance varibale

    private final static String MENU_SELECTED = "selected";
    private int selected = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);


        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        mrecyclerView.setNestedScrollingEnabled(false);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        viewModel.mLiveData().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                setupRecyclerView(results);
            }
        });

        viewModel.mLiveDataFav().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                Toast.makeText(MainActivity.this, "hurray", Toast.LENGTH_SHORT).show();
                setupRecyclerView(results);
            }
        });


       /* RemoteNetworkCall.getIntData().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                setupRecyclerView(results);
            }
        });*/


        //loadDefault("popular");
        //build("popular");

        //onSavedInstance loading if exist

       /* if (savedInstanceState != null) {
            selected = savedInstanceState.getInt(MENU_SELECTED);

            if (selected == -1) {

                loadDefault("top_rated");

            } else if (selected == R.id.highest_Rated) {

                loadDefault("popular");
            } else {

                loadDefault("top_rated");
            }

        }*/


    }

   /* private void setUpViewModel() {


        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Object obj=viewModel.getAllMovies();


        //setupRecyclerView(viewModel.getAllMovies());


    }*/

    private void setupRecyclerView(List<Result> results) {

        if (results != null) {

            mRecyclerMovie = new RecyclerMovie(MainActivity.this, results, new RecyclerMovie.ListItemClickListener() {
                @Override
                public void onListItemClick(Result movie) {

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("data", movie);
                    startActivity(intent);


                }
            });


            mrecyclerView.setAdapter(mRecyclerMovie);
            mRecyclerMovie.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "List Null", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadDefault(String sort) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiService.getMovies(sort, ApiClient.api_key);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, final Response<Example> response) {
                int statusCode = response.code();
                List<Result> results = response.body().getResults();
                mRecyclerMovie = new RecyclerMovie(MainActivity.this, results, new RecyclerMovie.ListItemClickListener() {
                    @Override
                    public void onListItemClick(Result movie) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("data", movie);
                        startActivity(intent);


                    }
                });


                mrecyclerView.setAdapter(mRecyclerMovie);
                mRecyclerMovie.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


    }


    //Creating inner class for Async Task

    /*public class MovieDbQUeryTask extends AsyncTask<URL, Void, List<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Movie> doInBackground(URL... urls) {

            List<Movie> result = NetworkUtils.fetchMovieData(urls[0]);
            return result;
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {


            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerMovie = new RecyclerMovie(MainActivity.this, movies, new RecyclerMovie.ListItemClickListener() {
                @Override
                public void onListItemClick(Movie movie, ImageView imageView) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("data", movie);
                    intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));

                    startActivity(intent);

                }
            });

            mrecyclerView.setAdapter(mRecyclerMovie);
            mRecyclerMovie.notifyDataSetChanged();

        }
    }
*/
    //onsaveInstanceState

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }


    // For menu settings

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.highest_Rated:
                //build("top_rated");
                // loadDefault("popular");
                viewModel.getTopRated();
                selected = id;

                break;

            case R.id.most_popular:
                //build("popular");
                //loadDefault("top_rated");
                viewModel.getPopular();
                selected = id;
                break;

            case R.id.fav:

                viewModel.getFavData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private URL build(String sort) {
        URL final_Url = NetworkUtils.buildURl(sort);
        new MovieDbQUeryTask().execute(final_Url);
        return final_Url;
    }*/
}
