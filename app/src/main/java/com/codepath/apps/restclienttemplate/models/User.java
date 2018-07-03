package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    // Attributes
    public Long uid;
    public String name;
    public String screenName;
    public String profileURL;

    public static User fromJSON (JSONObject object) throws JSONException {
        User user = new User();

        user.name = object.getString("name");
        user.uid = object.getLong("id");
        user.screenName = object.getString("screen_name");
        user.profileURL = object.getString("profile_image_url");

        return user;
    }
}
