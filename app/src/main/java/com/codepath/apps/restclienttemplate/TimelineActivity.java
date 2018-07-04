package com.codepath.apps.restclienttemplate;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class TimelineActivity extends AppCompatActivity {

    TwitterClient client;
    TweetAdapter adapter;
    ArrayList<Tweet> dataSource;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    private static String TAG = "TWITTERDEBUGGING";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApp.getRestClient(this);
        recyclerView = findViewById(R.id.rvTweet);
        dataSource = new ArrayList<>();
        adapter = new TweetAdapter(dataSource);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        populateTimeline();
        Toast.makeText(getApplicationContext(), "POPULATING...", Toast.LENGTH_LONG).show();
        fab = findViewById(R.id.fabCompose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "COMPOSE...", Toast.LENGTH_LONG).show();
            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.baby_blue)));

    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "Success 1");
                for (int i = 0; i < response.length(); i++) {
                    Tweet nTweet = new Tweet();
                    try {
                        nTweet = Tweet.fromJSON(response.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dataSource.add(nTweet);
                    adapter.notifyItemChanged(dataSource.size() - 1);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "Success 2");
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "on Failure 1");
                Log.d(TAG, throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "on Failure 2");
                Log.d(TAG, throwable.getMessage());
                Log.d(TAG, errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "on Failure 3");
                Log.d(TAG, throwable.getMessage());
                Log.d(TAG, errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89cff0")));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Toast toast = Toast.makeText(getApplicationContext(),"Option 1", Toast.LENGTH_SHORT);


        switch(item.getItemId()) {
            case R.id.refreshMenuOption:
                Log.i(TAG, "pressed");
                String title = getSupportActionBar().getTitle().toString();
                getSupportActionBar().setTitle("Refreshing.....");
                populateTimeline();
                getSupportActionBar().setTitle(title);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
