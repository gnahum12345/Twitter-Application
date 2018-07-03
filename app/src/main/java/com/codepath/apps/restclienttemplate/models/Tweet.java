package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Tweet {

    public Long uid;
    public String body;
    public String createdAt;
    public User user;
//    public Date date;

    public Tweet() {

    }

    // deserialize the JSON.
    public static Tweet fromJSON(JSONObject object) throws JSONException{
        Tweet tweet = new Tweet();
        tweet.body = object.getString("text");
        tweet.uid = object.getLong("id");
        tweet.user = User.fromJSON(object.getJSONObject("user"));
        tweet.createdAt = object.getString("created_at");
        return tweet;
    }
}
