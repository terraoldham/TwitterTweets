package com.codepath.apps.twittertweets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twittertweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapater extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapater(Context context, List<Tweet> tweets) {
        //super(context, android.R.layout.simple_list_item_1, tweets);
        super(context, 0, tweets);
    }

    /*
    public class ParseRelativeDate {
        // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
        public String getRelativeTimeAgo(String rawJsonDate) throws java.text.ParseException {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return relativeDate;
        }

        //Twitter date format Fri Apr 09 12:53:54 +0000 2010
    }
    */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get Tweets
        Tweet tweet = getItem(position);
        // Find or inflate template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // find subviews to fill with data
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
        // populate data into the subviews
        tvUserName.setText(tweet.getUser().getUserName());
        tvBody.setText(tweet.getBody());
        tvName.setText(tweet.getUser().getName());
        try {
            tvRelativeTime.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.getCreatedAt()));
            Log.d("DEBUG", "Good");

        } catch (java.text.ParseException e) {
            Log.d("DEBUG", "No good");
            e.printStackTrace();
        }

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        //Log.d("DEBUG", tweet.getUser().getProfileImageUrl());
        return convertView;
        // return view to be inserted into list
    }
}
