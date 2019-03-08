package com.example.ahmedabbas.popularmovies.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ahmedabbas.popularmovies.R;
import com.example.ahmedabbas.popularmovies.model.MoviesReviews;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<MoviesReviews> {

    ArrayList<MoviesReviews> moviesReviewsArrayList;
    Context context;

    public ReviewAdapter(Context context, ArrayList<MoviesReviews> moviesReviewsArrayList){
        super(context,0, moviesReviewsArrayList);
        this.context = context;
        this.moviesReviewsArrayList = moviesReviewsArrayList;
    }

    @Override
    public MoviesReviews getItem(int position) {
        return moviesReviewsArrayList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MoviesReviews moviesReviews = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_layout, parent, false);
        }

        TextView authorTextView = convertView.findViewById(R.id.author_name);
        authorTextView.setText(moviesReviews.getAuthor());

        TextView contentTextView = convertView.findViewById(R.id.review_content);
        contentTextView.setText(moviesReviews.getContent());

        return convertView;
    }
}
