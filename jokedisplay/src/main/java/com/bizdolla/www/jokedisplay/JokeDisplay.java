package com.bizdolla.www.jokedisplay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeDisplay extends AppCompatActivity {
    public static final String JOKE_EXTRA = "joke_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView tvJoke = (TextView) findViewById(R.id.tvJoke);
        CardView card =  (CardView) findViewById(R.id.jokecardview);
        card.setCardBackgroundColor( Color.WHITE);

        if(getIntent() != null && getIntent().hasExtra(JOKE_EXTRA)){
            String joke = getIntent().getStringExtra(JOKE_EXTRA);
            if(joke != null || !joke.equals("")) {
                tvJoke.setText(joke);
            }
            else{
                tvJoke.setText(getString(R.string.no_jokes_string));
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

