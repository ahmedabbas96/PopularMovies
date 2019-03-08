package com.example.ahmedabbas.popularmovies.utils;

import android.app.Application;

import com.example.ahmedabbas.popularmovies.model.Movies;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> favorite;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDataBase dataBase = AppDataBase.getInstance(this.getApplication());
        favorite = dataBase.moviesDAO().movieList();
    }

    public LiveData<List<Movies>> getFavorite (){
        return favorite;
    }
}
