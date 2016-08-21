package com.codepath.apps.twittertweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    private Button onCompose;
    public EditText tweetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tweetText = (EditText) findViewById(R.id.tweet_text);
        onCompose = (Button) findViewById(R.id.onCompose);
        //onCompose.setOnClickListener((View.OnClickListener) this);
    }

    public void onCompose(View v) {
        Toast.makeText(this, "Posting your Tweet", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ComposeActivity.this, TwitterClient.class);
        intent.putExtra("tweetText", tweetText.toString());
        startActivity(intent);

    }


}
