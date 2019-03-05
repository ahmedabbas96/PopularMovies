package com.example.ahmedabbas.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movies")
public class Movies implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "movieID")
    private int movieID;
    private String posterPath;
    private String title;
    private String releaseDate;
    private String voteAverage;
    private String overview;

    @ColumnInfo(name = "isFavorite")
    private int isFavorite;


    public Movies(Integer movieID, String posterPath, String title, String releaseDate, String voteAverage, String overview ){

        this.movieID = movieID;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.isFavorite = 0;

    }

    @Ignore
    protected Movies(Parcel in) {

        movieID = in.readInt();
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        isFavorite =in.readInt();
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
    // movie id
    public Integer getMovieID(){ return movieID; }
    public void setMovieID(Integer movieID){ this.movieID = movieID; }

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

    //isFavorite
    public Integer getIsFavorite () { return isFavorite; }

    public void setIsFavorite(int isFavorite) {this.isFavorite = isFavorite;}


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movies){
            Movies movies = (Movies)obj;
            return  movies.getMovieID().equals(getMovieID())&& movies.getPosterPath().equals( getPosterPath()) && movies.getTitle().equals(getTitle()) &&
                    movies.getReleaseDate().equals(getReleaseDate()) && movies.getVoteAverage().equals(getVoteAverage()) &&
                    movies.getOverview().equals(getOverview()) && movies.getIsFavorite().equals(getIsFavorite());
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

        dest.writeInt(movieID);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(overview);
        dest.writeInt(isFavorite);
    }


}
