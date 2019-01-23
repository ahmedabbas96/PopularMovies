package com.example.ahmedabbas.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    private String posterPath;
    private String title;
    private String releaseDate;
    private String voteAverage;
    private String overview;

    //default constructor
    public Movies(){}

    public Movies(String posterPath){
        this.posterPath = posterPath;
    }

    public Movies(String posterPath, String title, String releaseDate, String voteAverage, String overview ){

        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }


    protected Movies(Parcel in) {
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    //setters and getters
    // poster path
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    //title
    public String getTitle(){ return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    //releaseDate
    public String getReleaseDate(){ return releaseDate; }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    //voteAverage
    public String getVoteAverage(){ return voteAverage; }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    //overview
    public String getOverview(){ return overview; }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movies){
            Movies movies = (Movies)obj;
            return movies.getPosterPath().equals( getPosterPath()) && movies.getTitle().equals(getTitle()) &&
                    movies.getReleaseDate().equals(getReleaseDate()) && movies.getVoteAverage().equals(getVoteAverage()) &&
                    movies.getOverview().equals(getOverview());
        }else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(overview);
    }


}
