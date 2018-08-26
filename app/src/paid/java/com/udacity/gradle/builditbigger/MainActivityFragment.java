package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bizdolla.www.jokedisplay.JokeDisplay;
import com.github.ybq.android.spinkit.style.DoubleBounce;


public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    private final String TAG = MainActivityFragment.class.getSimpleName();
    private Context mContext;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar)root.findViewById(R.id.spin_kit);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        mContext = root.getContext();

        Button button = (Button)  root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveJoke();

            }
        });
        return root;
    }

    private void retrieveJoke() {
        Log.v(TAG,"Fetching Joke");
        progressBar.setVisibility(View.VISIBLE);

        new FetchJoke(new Listener() {
            @Override
            public void onJokeLoaded(String joke) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(mContext, JokeDisplay.class);
                intent.putExtra( JokeDisplay.JOKE_EXTRA, joke);
                startActivity(intent);
            }
        }).execute();
    }

}
