package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Users {
    public String name;
    public String userName;
    public String profilePicUrl;

    public Users() {
    }

    public static Users fromJson(JSONObject jsonObject) throws JSONException {
        Users user = new Users();

        user.name = jsonObject.getString("name");
        user.userName = jsonObject.getString("screen_name");
        user.profilePicUrl = jsonObject.getString("profile_image_url_https");

        return user;
    }
}
