package com.example.ahmedabbas.popularmovies;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedabbas.popularmovies.model.Movies;
import com.example.ahmedabbas.popularmovies.model.MoviesReviews;
import com.example.ahmedabbas.popularmovies.model.MoviesVideo;
import com.example.ahmedabbas.popularmovies.utils.AppDataBase;
import com.example.ahmedabbas.popularmovies.utils.AppExecutors;
import com.example.ahmedabbas.popularmovies.utils.HttpHelper;
import com.example.ahmedabbas.popularmovies.utils.JsonUtils;
import com.example.ahmedabbas.popularmovies.utils.ReviewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailedActivity extends AppCompatActivity {

    private AppDataBase mDb;

    Movies movies = null;

    private ArrayList<MoviesReviews> moviesReviewsArray;
    private ArrayList<MoviesVideo> moviesVideoArray;

    Button trailerbtn1, trailerbtn2, favoriteBtn;

    GridView reviewGridView;
    ReviewAdapter reviewAdapter;

    Boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        TextView ReleaseDateTv = findViewById(R.id.release_date);
        TextView MovieTitleTv = findViewById(R.id.movie_title_tv);
        TextView VoteAverageTv = findViewById(R.id.vote_average);
        TextView OverviewTv = findViewById(R.id.plot_synopsis);
        ImageView PoserIv = findViewById(R.id.poster_detailed_iv);
        trailerbtn1 = findViewById(R.id.trailer_btn1);
        trailerbtn2 = findViewById(R.id.trailer_btn2);
        reviewGridView = findViewById(R.id.grid_review);

        mDb = AppDataBase.getInstance(getApplicationContext());


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movies = intent.getExtras().getParcelable("movie");

        ReleaseDateTv.setText(movies.getReleaseDate());
        MovieTitleTv.setText(movies.getTitle());
        VoteAverageTv.setText(movies.getVoteAverage());
        OverviewTv.setText(movies.getOverview());
        Picasso.with(this).load(MainActivity.makePosterUrl(movies.getPosterPath())).into(PoserIv);


        getVideoUrl(MoviesVideo.getMovieVideo(movies.getMovieID().toString()));

        trailerbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(moviesVideoArray.get(0).getVideoKey());
            }
        });

        trailerbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(moviesVideoArray.get(1).getVideoKey());
            }
        });

        favoriteBtn = findViewById(R.id.favorite_btn);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int getFavorite  = mDb.moviesDAO().getFavorite(movies.getMovieID());
                isFavorite = getFavorite == 1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        favoriteBtn.setBackgroundResource(isFavorite ? R.drawable.star_green : R.drawable.star_gray);

                    }
                });
            }
        });



        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.favorite_btn) {
                v.setBackgroundResource(isFavorite ? R.drawable.star_gray : R.drawable.star_green);
                }
                onFavButton(movies);
            }
        });

        getMovieReview(MoviesReviews.getMovieReview(movies.getMovieID().toString()));

    }

    public void playVideo(String key){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
        }

        startActivity(intent);
    }

    public void getVideoUrl(String jsonUrl){
        new HttpHelper(new HttpHelper.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                moviesVideoArray = JsonUtils.parseMovieVideoJson(output);



            }
        }).execute(jsonUrl);
    }

    public void getMovieReview(String jsonUrl){
        new HttpHelper(new HttpHelper.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                moviesReviewsArray = JsonUtils.parseMoviesReviewsJson(output);

                //Log.e("Ahmed", "processFinish: "+moviesReviewsArray.get(0).getContent() );
                reviewAdapter = new ReviewAdapter(DetailedActivity.this,moviesReviewsArray);
                reviewGridView.setAdapter(reviewAdapter);

            }
        }).execute(jsonUrl);
    }

    public void onFavButton(final Movies movies){

        if(!isFavorite){

            movies.setIsFavorite(1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.moviesDAO().insertMovie(movies);

                }
            });

            Log.d("Ahmed", "onFavButton: "+movies.getIsFavorite());
        } else {
            movies.setIsFavorite(0);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.moviesDAO().deleteMovie(movies);

                }
            });
            Log.d("Ahmed", "onFavButton: "+movies.getIsFavorite());

        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }
}
