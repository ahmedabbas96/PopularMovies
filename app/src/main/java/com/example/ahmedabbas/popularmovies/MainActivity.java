package com.example.ahmedabbas.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.example.ahmedabbas.popularmovies.model.Movies;
import com.example.ahmedabbas.popularmovies.utils.GridAdapter;
import com.example.ahmedabbas.popularmovies.utils.HttpHelper;
import com.example.ahmedabbas.popularmovies.utils.InternetCheck;
import com.example.ahmedabbas.popularmovies.utils.JsonUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final String mostPopularUrl="https://api.themoviedb.org/3/movie/popular?api_key=";
    private final String highestRatedUrl="https://api.themoviedb.org/3/movie/top_rated?api_key=";

    private ArrayList<Movies> moviesArray;

    TextView opsTv, noInternetTv;

    Button retryBtn;

    GridView gridView;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link the layout
        gridView = findViewById(R.id.grid_view);
        opsTv = findViewById(R.id.ops_tv);
        noInternetTv = findViewById(R.id.no_internet_message_tv);
        retryBtn = findViewById(R.id.retry_btn);

        // Check internet
        InternetCheck();

        //onSaveInstanceState
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            populateUI(mostPopularUrl);
        }
        else {
            moviesArray = savedInstanceState.getParcelableArrayList("movies");
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LaunchDetailedActivity(moviesArray.get(position));
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

                    gridView.setVisibility(View.GONE);

                    opsTv.setVisibility(View.VISIBLE);
                    noInternetTv.setVisibility(View.VISIBLE);
                    retryBtn.setVisibility(View.VISIBLE);
                } else {

                    opsTv.setVisibility(View.GONE);
                    noInternetTv.setVisibility(View.GONE);
                    retryBtn.setVisibility(View.GONE);

                    gridView.setVisibility(View.VISIBLE);

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

                gridAdapter = new GridAdapter(MainActivity.this,moviesArray);
                gridView.setAdapter(gridAdapter);


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
