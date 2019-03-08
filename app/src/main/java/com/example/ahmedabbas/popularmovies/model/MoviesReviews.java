package com.example.ahmedabbas.popularmovies.model;


import com.example.ahmedabbas.popularmovies.MainActivity;

public class MoviesReviews {

    String author;
    String content;

    public MoviesReviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor(){ return author; }
    public void setAuthor(String author){ this.author = author; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content;}

    public static String getMovieReview(String movieID){

        // get review json
        return  "https://api.themoviedb.org/3/movie/"+movieID+"/reviews?api_key="+ MainActivity.apiKey;
    }
}
