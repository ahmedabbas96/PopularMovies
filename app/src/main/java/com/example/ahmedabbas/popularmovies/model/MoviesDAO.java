package com.example.ahmedabbas.popularmovies.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import androidx.lifecycle.LiveData;

@Dao
public interface MoviesDAO {

    @Query("SELECT * FROM movies")
    LiveData<List<Movies>> movieList();

    @Query("SELECT isFavorite FROM movies WHERE movieID = :movieID")
     int getFavorite(int movieID);

    @Insert
    void insertMovie(Movies movies);

    @Delete
    void deleteMovie(Movies movies);

}
