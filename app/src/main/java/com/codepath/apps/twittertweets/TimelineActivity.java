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
import android.widget.Toast;

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
    private final int REQUEST_CODE = 22;
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

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapater(this, tweets);
        lvTweets.setAdapter(aTweets);
        client = TwitterApplication.getRestClient();
        swipeTwitterRefresh();
        populateTimeline();

        /* lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Tweet tweet =
                Tweet tweet = (Tweet) lvTweets.getItemAtPosition(position);
                intent.putExtra("tweet", (Parcelable) tweet);
                startActivity(intent);
            }
        });
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onCompose(MenuItem mi) {
        Intent intent = new Intent(getApplicationContext(), ComposeActivity.class);
        startActivityForResult(intent, RESULT_OK);
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                aTweets.addAll(Tweet.fromJSONArray(json));
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void swipeTwitterRefresh() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTimeline();
            }
        });
    }

    private void refreshTimeline() {
        int numTweets = tweets.size();
        tweets.clear();
        aTweets.notifyDataSetChanged();

        populateTimeline();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent timelineIntent) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            refreshTimeline();
            //int code = intent.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen

        }
    }


}
