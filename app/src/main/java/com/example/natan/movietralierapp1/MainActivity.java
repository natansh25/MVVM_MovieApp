package com.example.natan.movietralierapp1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.natan.movietralierapp1.Adapter.RecyclerMovie;
import com.example.natan.movietralierapp1.ViewModel.MainViewModel;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.facebook.stetho.Stetho;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_main)
    CollapsingToolbarLayout mCollapsingMain;
    private int selected = 0;

    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    private RecyclerMovie mRecyclerMovie;
    MainViewModel viewModel;

    // onSaveinstance varibale

    private final static String MENU_SELECTED = "selected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintrial);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);
        setSupportActionBar(mToolbar);
        mCollapsingMain.setTitle("bLOCKbUSTERmOVIES");


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);


        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        mrecyclerView.setNestedScrollingEnabled(false);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            populateUI(selected);

        } else {
            selected = savedInstanceState.getInt(MENU_SELECTED);
            populateUI(selected);
        }



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int id= (int) viewHolder.itemView.getTag();
                viewModel.deleteData(id);

            }
        }).attachToRecyclerView(mrecyclerView);









    }

    private void populateUI(int i) {


        viewModel.mLiveData().removeObservers(this);
        // viewModel.mLiveDataFav().removeObservers(this);

        switch (i) {
            case 0:

                viewModel.mLiveData().observe(this, new Observer<List<MoviesResult>>() {
                    @Override
                    public void onChanged(@Nullable List<MoviesResult> results) {
                        setupRecyclerView(results);
                    }
                });

                break;

            case 1:

                viewModel.mLiveDataFav().observe(this, new Observer<List<MoviesResult>>() {
                    @Override
                    public void onChanged(@Nullable List<MoviesResult> results) {
                        setupRecyclerView(results);
                    }
                });


        }


    }

    private void setupRecyclerView(List<MoviesResult> results) {

        if (results != null) {

            mRecyclerMovie = new RecyclerMovie(MainActivity.this, results, new RecyclerMovie.ListItemClickListener() {
                @Override
                public void onListItemClick(MoviesResult movie) {

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

                viewModel.getTopRated();
                selected = 0;
                populateUI(selected);

                break;

            case R.id.most_popular:

                viewModel.getPopular();
                selected = 0;
                populateUI(selected);
                break;

            case R.id.fav:


                viewModel.getFavData();
                selected = 1;
                populateUI(selected);

                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
