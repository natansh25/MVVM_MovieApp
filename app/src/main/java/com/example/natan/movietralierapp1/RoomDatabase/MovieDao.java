package com.example.natan.movietralierapp1.RoomDatabase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.natan.movietralierapp1.model.Movies.MoviesResult;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MoviesResult result);


    @Query("SELECT * FROM movie_table")
    LiveData<List<MoviesResult>> getAllFav();

    @Query("DELETE FROM movie_table WHERE id=:id")
    void delete(int id);




}
