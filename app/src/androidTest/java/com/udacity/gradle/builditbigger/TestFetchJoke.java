package com.udacity.gradle.builditbigger;


import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;


public class TestFetchJoke extends AndroidTestCase {
    public void test(){
        new FetchJoke(new Listener() {
            @Override
            public void onJokeLoaded(String joke) {
                assertNotNull(joke);
                assertNotSame(joke,"");
            }
        }).execute();
    }
}
