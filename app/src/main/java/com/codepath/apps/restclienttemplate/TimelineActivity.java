package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
}
