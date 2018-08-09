package com.example.natan.movietralierapp1.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.natan.movietralierapp1.R;
import com.example.natan.movietralierapp1.model.Reviews.ReviewResult;

import java.util.List;

/**
 * Created by natan on 12/2/2017.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MyViewHolder> {

    List<ReviewResult> mMovieReviews;

    public MovieReviewAdapter(List<ReviewResult> movieReviews) {

        this.mMovieReviews = movieReviews;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ReviewResult movieReview = mMovieReviews.get(position);

        holder.txt_author.setText(movieReview.getAuthor() + " :");
        holder.txt_review.setText(movieReview.getContent());

    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_author, txt_review;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_author = itemView.findViewById(R.id.txtAuthor);
            // For underlining the textview
            txt_author.setPaintFlags(txt_author.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            txt_review = itemView.findViewById(R.id.txtReview);
        }
    }
}
