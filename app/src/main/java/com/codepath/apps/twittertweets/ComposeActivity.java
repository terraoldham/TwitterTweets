package com.codepath.apps.twittertweets;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.twittertweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;
    private Button onCompose;
    public EditText tweetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient();
        tweetText = (EditText) findViewById(R.id.tweet_text);
        onCompose = (Button) findViewById(R.id.onCompose);
        setupListeners();
        //onCompose.setOnClickListener((View.OnClickListener) this);
    }

    /* public void onCompose() {
        //TwitterClient client = new TwitterClient(getContext());
        Log.d("DEBUG", "Do we get here zero?");
        Toast.makeText(this, "Posting your Tweet", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ComposeActivity.this, TwitterClient.class);
        intent.putExtra("tweetText", tweetText.toString());
        startActivity(intent);
        Log.d("DEBUG", "Do we get here first?");

        // upon success, delay, then back to the other activity and see post
    } */

    private void setupListeners() {
        onCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postTweet();
            }
        });
    }

    private void postTweet() {
        final String status = tweetText.getText().toString();
        TwitterClient twitterClient = new TwitterClient(this);
        twitterClient.postStatus(new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);
                Intent intent = new Intent();
                intent.putExtra("status", status);
                intent.putExtra("code", 22);
                setResult(RESULT_OK, intent);
                finish();
            }
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("error", errorResponse.toString());
            }
        }, status);

        Intent timelineIntent = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(timelineIntent);
        setResult(RESULT_OK, timelineIntent);
        finish();
    }


    /*
    public void onCompose() {
        client.postTweet(new JsonHttpResponseHandler() {
            //Log.d("DEBUG", "Do we get here zero?");
            Toast.makeText(this, "Posting your Tweet", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ComposeActivity.this, TwitterClient.class);
            intent.putExtra("tweetText", tweetText.toString());
            startActivity(intent);
            //Log.d("DEBUG", "Do we get here first?");
            //Success
            @Override
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
            //Failure
        });
    }

    */

    /* Comment out for now
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get Tweets
        CurrentUser currentUser = ;
        // Find or inflate template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // find subviews to fill with data
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // populate data into the subviews
        tvUserName.setText(currentUser.getUserName());
        tvName.setText(currentUser.getName());
        ivProfileImage.setImageResource(android.R.color.transparent);
        //Picasso.with(getContext()).load(currentUser.getProfileImageUrl()).into(ivProfileImage);
        //Log.d("DEBUG", tweet.getUser().getProfileImageUrl());
        return convertView;
        // return view to be inserted into list
    }

    */

}
