package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class Tweet {

    public Long uid;
    public String body;
    public String createdAt;
    public User user;

    public Tweet() {

    }

    // deserialize the JSON.
    public static Tweet fromJSON(JSONObject object) throws JSONException{
        Tweet tweet = new Tweet();
        tweet.body = object.getString("text");
        tweet.uid = object.getLong("id");
        tweet.user = User.fromJSON(object.getJSONObject("user"));
        tweet.createdAt = object.getString("created_at");
        Log.i(TAG, tweet.getRelativeTimeAgo());
        return tweet;
    }

    public String getRelativeTimeAgo() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.getDefault());
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(createdAt).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
