package com.example.natan.movietralierapp1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.movietralierapp1.Adapter.MovieReviewAdapter;
import com.example.natan.movietralierapp1.Adapter.MovieTrailerAdapter;
import com.example.natan.movietralierapp1.ViewModel.DetailViewModel;
import com.example.natan.movietralierapp1.ViewModel.DetailViewModelFactory;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;
import com.example.natan.movietralierapp1.model.Trailer.TrailerResult;
import com.example.natan.movietralierapp1.picasso.RoundedTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.release)
    TextView txt_Release;
    @BindView(R.id.title)
    TextView txt_Title;
    @BindView(R.id.image_poster)
    ImageView img_Poster;
    @BindView(R.id.plot)
    TextView txt_Plot;
    @BindView(R.id.app_bar_image)
    ImageView app_bar_img;
    @BindView(R.id.ratingbar)
    RatingBar mRatingbar;

    DetailViewModel mDetailViewModel;
    MoviesResult mResult;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingtoolbar)
    CollapsingToolbarLayout mCollapsingtoolbar;
    @BindView(R.id.fab_detail)
    FloatingActionButton mFabDetail;
    private int id;
    // for Trailer
    private RecyclerView mRecyclerView;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    //for review
    private RecyclerView mRecyclerViewReview;
    private MovieReviewAdapter mMovieReviewAdapter;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailfinal);
        ButterKnife.bind(this);
        postponeEnterTransition();
        mRecyclerView = findViewById(R.id.recycler_Trailers);
        mRecyclerViewReview = findViewById(R.id.recycler_review);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        // for trailer adapter----------------------------------------------------------------------
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //for review adapter------------------------------------------------------------------------
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setAutoMeasureEnabled(true);
        mRecyclerViewReview.setLayoutManager(manager);
        mRecyclerViewReview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerViewReview.setItemAnimator(new DefaultItemAnimator());


        MoviesResult movie = getIntent().getParcelableExtra("data");
        mResult = movie;
        String name = getIntent().getExtras().getString(MainActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
        Float rating = Float.valueOf(movie.getVoteCount());
        mCollapsingtoolbar.setTitle(mResult.getOriginalTitle());
        DetailViewModelFactory factory = new DetailViewModelFactory(mResult.getId(), getApplicationContext());


        mDetailViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        Float cal = (5 * rating) / 10;

        mRatingbar.setRating(cal);

        img_Poster.setTransitionName(name);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getBackdropPath()).into(app_bar_img);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + mResult.getPosterPath()).transform(new RoundedTransformation(20, 0)).into(img_Poster, new Callback() {
            @Override
            public void onSuccess() {
                startPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                startPostponedEnterTransition();
            }
        });


        txt_Title.setText(movie.getTitle());
        txt_Plot.setText(movie.getOverview());
        /*txt_Rating.setText(movie.getVoteAverage() + "/10");*/
        txt_Release.setText(movie.getReleaseDate());


        mDetailViewModel.getAllReviews().observe(this, new Observer<List<ReviewResult>>() {
            @Override
            public void onChanged(@Nullable List<ReviewResult> reviewResults) {
                Log.d("reviewsxxx", String.valueOf(reviewResults));
                mMovieReviewAdapter = new MovieReviewAdapter(reviewResults);
                mRecyclerViewReview.setAdapter(mMovieReviewAdapter);

            }
        });

        mDetailViewModel.getAllTrailers().observe(this, new Observer<List<TrailerResult>>() {
            @Override
            public void onChanged(@Nullable List<TrailerResult> trailerResults) {
                Log.d("trailerxxx", String.valueOf(trailerResults));
                mMovieTrailerAdapter = new MovieTrailerAdapter(trailerResults, new MovieTrailerAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(TrailerResult movieTrailer) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movieTrailer.getKey()));
                        startActivity(intent);



                    }
                });
                mRecyclerView.setAdapter(mMovieTrailerAdapter);
            }
        });


    }


    @OnClick(R.id.fab_detail)
    public void onViewClicked() {
        mDetailViewModel.insert(mResult);
        Toast.makeText(this, mResult.getOriginalTitle()+" "+"added to favorites!!", Toast.LENGTH_SHORT).show();
    }
}
