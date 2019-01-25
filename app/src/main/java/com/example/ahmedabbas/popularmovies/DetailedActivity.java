package com.example.ahmedabbas.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedabbas.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        TextView ReleaseDateTv = findViewById(R.id.release_date);
        TextView MovieTitleTv = findViewById(R.id.movie_title_tv);
        TextView VoteAverageTv = findViewById(R.id.vote_average);
        TextView OverviewTv = findViewById(R.id.plot_synopsis);
        ImageView PoserIv = findViewById(R.id.poster_detailed_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Movies movies = intent.getExtras().getParcelable("movie");

        ReleaseDateTv.setText(movies.getReleaseDate());
        MovieTitleTv.setText(movies.getTitle());
        VoteAverageTv.setText(movies.getVoteAverage());
        OverviewTv.setText(movies.getOverview());
        Picasso.with(this).load(MainActivity.makePosterUrl(movies.getPosterPath())).into(PoserIv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }
}
