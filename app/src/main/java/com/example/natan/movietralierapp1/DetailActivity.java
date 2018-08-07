package com.example.natan.movietralierapp1;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.natan.movietralierapp1.ViewModel.DetailViewModel;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.picasso.RoundedTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.release)
    TextView txt_Release;
    /*    @BindView(R.id.rating)
        TextView txt_Rating;*/
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
    @BindView(R.id.btn_fav)
    Button mBtnFav;
    DetailViewModel mDetailViewModel;
    MoviesResult mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        postponeEnterTransition();
        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);


        //getActionBar().setDisplayHomeAsUpEnabled(true);


        MoviesResult movie = getIntent().getParcelableExtra("data");
        mResult = movie;
        String name = getIntent().getExtras().getString(MainActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
        Float rating = Float.valueOf(movie.getVoteCount());
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


    }

    @OnClick(R.id.btn_fav)
    public void onViewClicked() {

        mDetailViewModel.insert(mResult);

    }
}
