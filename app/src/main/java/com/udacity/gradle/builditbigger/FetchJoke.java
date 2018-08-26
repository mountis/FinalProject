package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builtitbigger.backend.myApi.MyApi;

import java.io.IOException;


public class FetchJoke extends AsyncTask<Void, Void, String> {

    public static String TAG = FetchJoke.class.getSimpleName();
    private Listener mListener;
    private static MyApi myApiService = null;

    public FetchJoke(Listener listener) {
        mListener = listener;
    }
    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-214019.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.tellaJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mListener.onJokeLoaded(joke);
    }

}
