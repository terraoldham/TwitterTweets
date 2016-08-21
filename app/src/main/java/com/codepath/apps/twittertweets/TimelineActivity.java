package com.codepath.apps.twittertweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.twittertweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapater aTweets;
    private ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // find listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // create array list
        tweets = new ArrayList<>();
        // construct
        aTweets = new TweetsArrayAdapater(this, tweets);
        // connect
        lvTweets.setAdapter(aTweets);
        // get client
        client = TwitterApplication.getRestClient();
        populateTimeline();


    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", json.toString());
                // JSON
                // Deserialize
                // Create models and add to adapter
                // Load model data into list view
                //ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                aTweets.addAll(Tweet.fromJSONArray(json));
                Log.d("DEBUG", aTweets.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                //Log.d("DEBUG", errorResponse.toString());
            }
            //Failure
        });
    }

}
