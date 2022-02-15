package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.adapters.TimelineAdapter;
import com.codepath.apps.restclienttemplate.models.Tweets;
import com.codepath.apps.restclienttemplate.networking.TwitterClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    TwitterClient client;
    List<Tweets> tweets;
    RecyclerView rvTimeline;
    TimelineAdapter adapter;
    private TextView tvTextBody;
    private TextView tvUserName;
   //private TextView tvDate;
    private ImageView ivProfilePic;


    public static String  TAG = "TimelineActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);
        rvTimeline = findViewById(R.id.rvTimeline);
        tweets = new ArrayList<>();
        adapter = new TimelineAdapter(this,tweets);
        rvTimeline.setLayoutManager(new LinearLayoutManager(this));
        rvTimeline.setAdapter(adapter);
        retrieveHomeTimeline();

        adapter.setOnItemClickListener(new TimelineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                tvTextBody = itemView.findViewById(R.id.tvTextBody);
                tvUserName = itemView.findViewById(R.id.tvUserName);
                //tvDate = itemView.findViewById(R.id.tvDate);
                ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

                Intent i = new Intent(TimelineActivity.this, TweetDetailActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweets.get(position)));

                Pair<View, String> p1 = Pair.create(tvTextBody, "body");
                Pair<View, String> p2 = Pair.create(tvUserName, "name");
                Pair<View, String> p3 = Pair.create(ivProfilePic, "pic");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(TimelineActivity.this, p1, p2, p3);
                startActivity(i, options.toBundle());
            }
        });
    }

    private void retrieveHomeTimeline() {
        client.getTwitterTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Timeline was retrieved.");
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweets.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Json error", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Error in getting timeline." + response, throwable);
            }
        });
    }
}
