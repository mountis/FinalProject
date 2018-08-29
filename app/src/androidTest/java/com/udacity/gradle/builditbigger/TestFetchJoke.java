package com.udacity.gradle.builditbigger;


import android.util.Log;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class TestFetchJoke extends AndroidTestCase {
    private static final String TAG = TestFetchJoke.class.getSimpleName();
    private Listener mListner;

    public void testFetchesNonEmptyString() {
        FetchJoke fetchJoke = new FetchJoke( mListner );
        fetchJoke.execute();
        try {
            String joke = fetchJoke.get();
            Log.d( TAG, "Joke text: " + joke );
            assertNotNull( joke );
            assertTrue( joke.length() > 0 );
        } catch (InterruptedException | ExecutionException e) {
            Log.e( TAG, Log.getStackTraceString( e ) );
        }
    }
}
