package com.codepath.apps.twittertweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by toldham on 8/19/16.
 */
public class User {
    //list attributes
    private String name;
    private long uid;
    private String userName;
    private String profileImageUrl;
    //deserialize


    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getUserName() {

        return "@" + userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.userName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // extract and fill
        //return user
        return u;
    }
}
