package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.models.Tweets;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class TweetDetailActivity extends AppCompatActivity {
    private TextView tvTextBody;
    private TextView tvName;
    private TextView tvUserName;
    private TextView tvDate;
    private ImageView ivProfilePic;
    Button btReply;
    private final int REQUEST_CODE = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitter_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        tvTextBody = findViewById(R.id.tvTextBody);
        tvUserName = findViewById(R.id.tvUserName);
        tvName = findViewById(R.id.tvName);
        tvDate = findViewById(R.id.tvDate);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        btReply = findViewById(R.id.btReply);

        Tweets tweet = (Tweets) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvTextBody.setText(tweet.body);
        tvUserName.setText("@" + tweet.user.userName);
        tvName.setText(tweet.user.name);
        tvDate.setText(TimeFormatter.getTimeStamp(tweet.dateCreated));
        Glide.with(TweetDetailActivity.this)
                .load(tweet.user.profilePicUrl)
                .transform(new CropCircleTransformation())
                .into(ivProfilePic);
        btReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TweetDetailActivity.this, ReplyActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweet));
                startActivityForResult(i, REQUEST_CODE);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
