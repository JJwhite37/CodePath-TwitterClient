package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.models.Tweets;
import com.codepath.apps.restclienttemplate.networking.TwitterClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ReplyActivity extends AppCompatActivity {
    public static String  TAG = "ComposeActivity";
    EditText etCompose;
    TextInputLayout tfCompose;
    Button btTweet;
    TwitterClient client;
    public long id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        etCompose = findViewById(R.id.etCompose);
        btTweet = findViewById(R.id.btTweet);
        tfCompose = findViewById(R.id.tfCompose);
        Tweets tweets = (Tweets) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tfCompose.setHint("In reply to @" +tweets.user.userName);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweet = etCompose.getText().toString();

                if (tweet.isEmpty()) {
                    Toast.makeText(ReplyActivity.this, "no characters where typed", Toast.LENGTH_LONG).show();
                    return;
                } if(tweet.length() > 280) {
                    Toast.makeText(ReplyActivity.this, "Too many characters typed", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(ReplyActivity.this, tweet, Toast.LENGTH_LONG).show();
                client = TwitterApp.getRestClient(ReplyActivity.this);
                tweet = "@" + tweets.user.userName + " " + tweet;
                id = tweets.id;
                client.postReply(tweet,id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "Tweet posted success.");
                        try {
                            Tweets tweet = Tweets.fromJson(json.jsonObject);
                            Intent i = new Intent();
                            i.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, i);
                            finish();
                        } catch (JSONException e) {
                            Log.e(TAG, "Error in json object.");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "Error in posting tweet." + response, throwable);
                    }
                });
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}