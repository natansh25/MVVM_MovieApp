package com.example.natan.movietralierapp1.RoomDatabase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.natan.movietralierapp1.model.Movies.MoviesResult;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(MoviesResult result);


    @Query("SELECT * FROM MoviesResult")
    LiveData<List<MoviesResult>> getAllFav();


}
