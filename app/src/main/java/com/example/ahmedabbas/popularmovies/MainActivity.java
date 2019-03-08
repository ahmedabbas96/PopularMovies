package com.example.ahmedabbas.popularmovies;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.example.ahmedabbas.popularmovies.model.Movies;
import com.example.ahmedabbas.popularmovies.utils.AppDataBase;
import com.example.ahmedabbas.popularmovies.utils.GridAdapter;
import com.example.ahmedabbas.popularmovies.utils.HttpHelper;
import com.example.ahmedabbas.popularmovies.utils.InternetCheck;
import com.example.ahmedabbas.popularmovies.utils.JsonUtils;
import com.example.ahmedabbas.popularmovies.utils.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    public static final String apiKey ="";
    private final String mostPopularUrl="https://api.themoviedb.org/3/movie/popular?api_key="+apiKey;
    private final String highestRatedUrl="https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey;

    private ArrayList<Movies> moviesArray;

    private LiveData<List<Movies>> favoriteMovies;

    TextView opsTv, noInternetTv, nofav;

    Button retryBtn;

    GridView gridView;
    GridAdapter gridAdapter;

    private AppDataBase mDb;

    boolean isFavoritePressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link the layout
        gridView = findViewById(R.id.grid_view);
        opsTv = findViewById(R.id.ops_tv);
        noInternetTv = findViewById(R.id.no_internet_message_tv);
        retryBtn = findViewById(R.id.retry_btn);
        nofav = findViewById(R.id.no_fav_message_tv);

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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if(isFavoritePressed){
                    isFavoritePressed = false;
                    favoriteMovies.observe(MainActivity.this, new Observer<List<Movies>>() {
                        @Override
                        public void onChanged(List<Movies> movies) {
                            LaunchDetailedActivity(movies.get(position));
                        }
                    });
                }else {
                    LaunchDetailedActivity(moviesArray.get(position));
                }

            }
        });


        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetCheck();
            }
        });

        mDb = AppDataBase.getInstance(getApplicationContext());

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
        else if(id == R.id.favorite_item){

            isFavoritePressed = true;

            MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            viewModel.getFavorite().observe(this, new Observer<List<Movies>>() {
                @Override
                public void onChanged(List<Movies> movies) {

                    if(movies.size() == 0){

                        gridView.setVisibility(View.GONE);
                        nofav.setVisibility(View.VISIBLE);

                    }else {
                        gridView.setVisibility(View.VISIBLE);
                        gridAdapter = new GridAdapter(MainActivity.this, (ArrayList<Movies>) movies);
                        gridView.setAdapter(gridAdapter);
                    }
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }
}
