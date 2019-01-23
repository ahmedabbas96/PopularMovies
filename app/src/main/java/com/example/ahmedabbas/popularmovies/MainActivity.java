package com.example.ahmedabbas.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ahmedabbas.popularmovies.model.Movies;
import com.example.ahmedabbas.popularmovies.utils.HttpHelper;
import com.example.ahmedabbas.popularmovies.utils.InternetCheck;
import com.example.ahmedabbas.popularmovies.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String mostPopularUrl="https://api.themoviedb.org/3/movie/popular?api_key=";
    private final String highestRatedUrl="https://api.themoviedb.org/3/movie/top_rated?api_key=";

    private ArrayList<Movies> moviesArray;

     public static boolean internet;

    ImageView Poster1, Poster2, Poster3, Poster4;

    TextView opsTv, noInternetTv;

    Button retryBtn;

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link the layout
        gridLayout = findViewById(R.id.grid_layout);
        opsTv = findViewById(R.id.ops_tv);
        noInternetTv = findViewById(R.id.no_internet_message_tv);
        retryBtn = findViewById(R.id.retry_btn);
        Poster1 = findViewById(R.id.posterIV1);
        Poster2 = findViewById(R.id.posterIV2);
        Poster3 = findViewById(R.id.posterIV3);
        Poster4 = findViewById(R.id.posterIV4);

        // Check internet
        InternetCheck();

        //onSaveInstanceState
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            populateUI(mostPopularUrl);
        }
        else {
            moviesArray = savedInstanceState.getParcelableArrayList("movies");
        }

        Poster1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchDetailedActivity(moviesArray.get(0));
            }
        });

        Poster2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchDetailedActivity(moviesArray.get(1));
            }
        });

        Poster3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchDetailedActivity(moviesArray.get(2));
            }
        });

        Poster4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchDetailedActivity(moviesArray.get(3));
            }
        });


        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetCheck();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", moviesArray);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getParcelableArrayList("movies");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void InternetCheck(){

        new InternetCheck(new InternetCheck.InternetState() {
            @Override
            public void processFinish(Boolean state) {
                if(!state){

                    gridLayout.setVisibility(View.GONE);

                    opsTv.setVisibility(View.VISIBLE);
                    noInternetTv.setVisibility(View.VISIBLE);
                    retryBtn.setVisibility(View.VISIBLE);
                } else {

                    opsTv.setVisibility(View.GONE);
                    noInternetTv.setVisibility(View.GONE);
                    retryBtn.setVisibility(View.GONE);

                    gridLayout.setVisibility(View.VISIBLE);

                }

            }
        }).execute();
    }

    private void LaunchDetailedActivity(Movies movies){
        Intent intent = new Intent(this,DetailedActivity.class);
        intent.putExtra("movie", movies);
        startActivity(intent);
    }

    public void populateUI(String url){

        new HttpHelper(new HttpHelper.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                moviesArray = JsonUtils.parseMoviesJson(output);

                Picasso.with(MainActivity.this).load(makePosterUrl(moviesArray.get(0).getPosterPath())).into(Poster1);
                Picasso.with(MainActivity.this).load(makePosterUrl(moviesArray.get(1).getPosterPath())).into(Poster2);
                Picasso.with(MainActivity.this).load(makePosterUrl(moviesArray.get(2).getPosterPath())).into(Poster3);
                Picasso.with(MainActivity.this).load(makePosterUrl(moviesArray.get(3).getPosterPath())).into(Poster4);


            }
        }).execute(url);
    }

    public static String makePosterUrl(String poster_path){

        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE_KEY= "w185/";

       return POSTER_BASE_URL+SIZE_KEY+poster_path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.most_popular_item){

            populateUI(mostPopularUrl);

        }
        else if(id == R.id.highest_rated_item){

            populateUI(highestRatedUrl);

        }
        return super.onOptionsItemSelected(item);
    }
}
