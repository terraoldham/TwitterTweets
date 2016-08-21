package com.codepath.apps.twittertweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    private Button onCompose;
    private TextView tweetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tweetText = (TextView) findViewById(R.id.tweet_text);
        onCompose = (Button) findViewById(R.id.onCompose);
        //onCompose.setOnClickListener((View.OnClickListener) this);
    }

    public void onCompose(View v) {
        // do something when the button is clicked
        Toast.makeText(this, "Posting your Tweet", Toast.LENGTH_LONG).show();
    }


}
