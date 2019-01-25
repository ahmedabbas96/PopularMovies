package com.example.ahmedabbas.popularmovies.utils;

import android.os.AsyncTask;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetCheck extends AsyncTask<Void, Void, Boolean> {

    public interface InternetState{
        void processFinish(Boolean state);
    }

    private InternetState internetState;

    public InternetCheck(InternetState internetState){ this.internetState = internetState; }

    @Override
    protected Boolean doInBackground(Void... voids) {

        Socket sock = new Socket();
        try {
            sock.connect(new InetSocketAddress("8.8.8.8",53),1500);
            sock.close();
            return true;

        } catch (IOException e) {
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean internet) {

        internetState.processFinish(internet);
    }
}
