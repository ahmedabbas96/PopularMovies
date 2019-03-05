package com.example.ahmedabbas.popularmovies.utils;


import android.util.Log;

import com.example.ahmedabbas.popularmovies.model.Movies;
import com.example.ahmedabbas.popularmovies.model.MoviesReviews;
import com.example.ahmedabbas.popularmovies.model.MoviesVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    //movie keys
    private final static String RESULT_KEY = "results";
    private final static String POSTER_PATH_KEY = "poster_path";
    private final static String TITLE_KEY = "title";
    private final static String RELEASE_DATE_KEY = "release_date";
    private final static String VOTE_AVERAGE_KEY = "vote_average";
    private final static String PLOT_SYNOPSIS_KEY = "overview";
    private final static String MOVIE_ID_KEY="id";

    //review keys
    private final static String AUTHOR_KEY = "author";
    private final static String CONTENT_KEY = "content";

    //videos keys
    private final static String VIDEO_KEY = "key";

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
                    Integer movieID = movieJsonObject.getInt(MOVIE_ID_KEY);

                    Movies movie = new Movies(movieID,posterPath,movieTitle,releaseDate,voteAverage,plotSynopsis);
                    moviesArray.add(movie);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return moviesArray;
    }

    //method to get review json

    public static ArrayList<MoviesReviews> parseMoviesReviewsJson (String json){

        ArrayList<MoviesReviews> moviesReviewsArray = new ArrayList<>();

        if(json != null){

            try {
                JSONObject mainJsonObject = new JSONObject(json);

                JSONArray resultJsonArray = mainJsonObject.getJSONArray(RESULT_KEY);

                for(int i = 0; i < resultJsonArray.length(); i++){
                    JSONObject reviewsJsonObject = resultJsonArray.getJSONObject(i);
                    String reviewsAuthor = reviewsJsonObject.getString(AUTHOR_KEY);
                    String reviewsContent = reviewsJsonObject.getString(CONTENT_KEY);

                    MoviesReviews moviesReviews = new MoviesReviews(reviewsAuthor,reviewsContent);
                    moviesReviewsArray.add(moviesReviews);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return moviesReviewsArray;
    }

    // method to get movies trailers

    public static ArrayList<MoviesVideo> parseMovieVideoJson (String json){

        ArrayList<MoviesVideo> moviesVideoArray = new ArrayList<>();

        if(json != null){

            try {
                JSONObject mainJsonObject = new JSONObject(json);

                JSONArray resultJsonArray = mainJsonObject.getJSONArray(RESULT_KEY);

                for(int i = 0; i < resultJsonArray.length(); i++){
                    JSONObject videoJsonObject = resultJsonArray.getJSONObject(i);
                    String moviesKey = videoJsonObject.getString(VIDEO_KEY);

                    MoviesVideo moviesVideo = new MoviesVideo(moviesKey);
                    moviesVideoArray.add(moviesVideo);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return moviesVideoArray;
    }

}
