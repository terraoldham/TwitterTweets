package com.codepath.apps.twittertweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by toldham on 8/19/16.
 */
public class CurrentUser {
    //list attributes
    private String name;
    private String userName;
    private String profileImageUrl;
    //deserialize


    public String getName() {
        return name;
    }


    public String getUserName() {

        return "@" + userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static CurrentUser fromJSON(JSONObject json) {
        CurrentUser u = new CurrentUser();
        try {
            u.name = json.getString("name");
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
