
package com.example.natan.movietralierapp1.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.natan.movietralierapp1.MainActivity;
import com.example.natan.movietralierapp1.model.Movies.MoviesResult;
import com.example.natan.movietralierapp1.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

import com.example.natan.movietralierapp1.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by natan on 11/20/2017.
 */

public class RecyclerMovie extends RecyclerView.Adapter<RecyclerMovie.MyViewHolder> {


    private List<MoviesResult> mMovieList;
    //Implementing on click listner
    final private ListItemClickListener mOnClickListener;

    //Interface

    public interface ListItemClickListener {

        void onListItemClick(MoviesResult movie);
    }


    public RecyclerMovie(MainActivity mainActivity, List<MoviesResult> movieList, ListItemClickListener listener) {
        mMovieList = movieList;
        mOnClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MoviesResult movie = mMovieList.get(position);
        holder.itemView.setTag(movie.getId());

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(14, 0)).into(holder.img_movie);
        // holder.bind(mMovieList.get(position), mOnClickListener);
        //ViewCompat.setTransitionName(holder.img_movie, movie.getTitle());


    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void clearList() {
        if (mMovieList == null) {
            mMovieList = new ArrayList<>();
        } else {
            int itemCount = mMovieList.size();
            mMovieList.clear();
            notifyItemRangeRemoved(0, itemCount);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView)
        ImageView img_movie;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            MoviesResult result = mMovieList.get(adapterPosition);
            mOnClickListener.onListItemClick(result);


        }
    }
}
