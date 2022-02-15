package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweets {

    public String body;
    public String dateCreated;
    public Users user;

    public Tweets() {
    }

    public static Tweets fromJson(JSONObject jsonObject) throws JSONException {
        Tweets tweet = new Tweets();

        tweet.body = jsonObject.getString("text");
        tweet.dateCreated = jsonObject.getString("created_at");
        tweet.user = Users.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

    public static List<Tweets> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweets> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
