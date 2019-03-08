package com.example.ahmedabbas.popularmovies.model;

import androidx.room.Entity;

import com.example.ahmedabbas.popularmovies.MainActivity;

@Entity(tableName = "videos")
public class MoviesVideo {

    String videoKey;

    public MoviesVideo(String videoKey){

        this.videoKey = videoKey;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey){ this.videoKey = videoKey; }

    public static String getMovieVideo(String movieID){

        // get video json
        return "https://api.themoviedb.org/3/movie/"+movieID+"/videos?api_key="+ MainActivity.apiKey;
    }

}

