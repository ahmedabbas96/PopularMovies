package com.example.ahmedabbas.popularmovies.utils;


import android.util.Log;

import com.example.ahmedabbas.popularmovies.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private final static String RESULT_KEY = "results";
    private final static String POSTER_PATH_KEY = "poster_path";
    private final static String TITLE_KEY = "title";
    private final static String RELEASE_DATE_KEY = "release_date";
    private final static String VOTE_AVERAGE_KEY = "vote_average";
    private final static String PLOT_SYNOPSIS_KEY = "overview";

    public static ArrayList<Movies> parseMoviesJson(String json){

        ArrayList<Movies> moviesArray = new ArrayList<>();

        if(json != null){
            try {
                JSONObject mainJsonObject = new JSONObject(json);

                JSONArray resultJsonArray = mainJsonObject.getJSONArray(RESULT_KEY);

                for(int i = 0; i < resultJsonArray.length(); i++){
                    JSONObject movieJsonObject = resultJsonArray.getJSONObject(i);
                    String posterPath = movieJsonObject.getString(POSTER_PATH_KEY);
                    String movieTitle = movieJsonObject.getString(TITLE_KEY);
                    String releaseDate = movieJsonObject.getString(RELEASE_DATE_KEY);
                    String voteAverage = movieJsonObject.getString(VOTE_AVERAGE_KEY);
                    String plotSynopsis = movieJsonObject.getString(PLOT_SYNOPSIS_KEY);

                    Movies movie = new Movies(posterPath,movieTitle,releaseDate,voteAverage,plotSynopsis);
                    moviesArray.add(movie);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return moviesArray;
    }

}
