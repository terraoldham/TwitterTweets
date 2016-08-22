package com.codepath.apps.twittertweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.twittertweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    TwitterClient client;
    ArrayList<Tweet> tweets;
    TweetsArrayAdapater aTweets;
    ListView lvTweets;
    /*     private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapater aTweets;
    private ListView lvTweets;
    */
    SwipeRefreshLayout swipeContainer;


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

        swipeTwitterRefresh();
        populateTimeline();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onCompose(MenuItem mi) {
        Intent intent = new Intent(getApplicationContext(), ComposeActivity.class);
        startActivity(intent);
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
                //Log.d("DEBUG", aTweets.toString());
                swipeContainer.setRefreshing(false);
                //Log.d("DEBUG", "Refresh number");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                //Log.d("DEBUG", errorResponse.toString());
                swipeContainer.setRefreshing(false);
                //Log.d("DEBUG", "Failure number");
            }
            //Failure
        });
    }

    private void swipeTwitterRefresh() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
                //Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                //Log.d("DEBUG", "is this actually registering?");
                populateTimeline();

            }
        });
    }

}
