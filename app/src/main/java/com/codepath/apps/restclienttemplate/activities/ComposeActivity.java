package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;

public class ComposeActivity extends AppCompatActivity {
    EditText etCompose;
    Button btTweet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etCompose = findViewById(R.id.etCompose);
        btTweet = findViewById(R.id.btTweet);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweet = etCompose.getText().toString();

                if (tweet.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "no characters where typed", Toast.LENGTH_LONG).show();
                    return;
                } if(tweet.length() > 280) {
                    Toast.makeText(ComposeActivity.this, "Too many characters typed", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this, tweet, Toast.LENGTH_LONG).show();
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
