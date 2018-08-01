package com.example.natan.movietralierapp1.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by natan on 11/19/2017.
 */

public class Movie implements Parcelable {

    private String mTitle, mReleaseDate, mOverview, mImage, mVoteAverage, mBackImage;


    public Movie(String image) {
        mImage = image;
    }

    public Movie(String image, String title, String releaseDate, String voteAverage, String overview) {
        mImage = image;
        mTitle = title;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mOverview = overview;
    }

    public String getBackImage() {
        return mBackImage;
    }

    public void setBackImage(String backImage) {
        mBackImage = backImage;
    }

    public Movie(String image, String title, String releaseDate, String voteAverage, String overview, String backImage) {

        mTitle = title;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mImage = image;
        mVoteAverage = voteAverage;
        mBackImage = backImage;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mOverview);
        dest.writeString(this.mImage);
        dest.writeString(this.mVoteAverage);
        dest.writeString(this.mBackImage);
    }

    protected Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mOverview = in.readString();
        this.mImage = in.readString();
        this.mVoteAverage = in.readString();
        this.mBackImage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
