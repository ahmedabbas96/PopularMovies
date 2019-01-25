package com.example.ahmedabbas.popularmovies.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.ahmedabbas.popularmovies.MainActivity;
import com.example.ahmedabbas.popularmovies.R;
import com.example.ahmedabbas.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Movies> {

    ArrayList<Movies> moviesArrayList;
    Context context;

    public GridAdapter(Context context, ArrayList<Movies> moviesArrayList){
        super(context,0,moviesArrayList);
        this.context = context;
        this.moviesArrayList = moviesArrayList;
    }

    @Override
    public Movies getItem(int position) {
        return moviesArrayList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movies movie = getItem(position);

        if(convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_layout, parent, false);
        }

            ImageView PosterImage = convertView.findViewById(R.id.poster_iv);

            Picasso.with(context).load(MainActivity.makePosterUrl(movie.getPosterPath())).into(PosterImage);



        return convertView;
    }
}
