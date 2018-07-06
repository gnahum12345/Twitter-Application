package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.ParseException;

public class ComposeActivity extends AppCompatActivity {


    EditText composeText;
    TextView tvCharacterCount;
    Button cancelButton;
    boolean enableTweet;
    private TwitterClient client;
    private final int COMPOSE_RESULT_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        cancelButton = findViewById(R.id.cancelCompose);
        composeText = findViewById(R.id.etBody);
        tvCharacterCount = findViewById(R.id.tvCounter);

        client = TwitterApp.getRestClient(this);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cancelling tweet....", 0).show();
                finish();
            }
        });
        composeText.setTextColor(Color.BLACK);

        composeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             tvCharacterCount.setText(Integer.toString(140 - composeText.length()) + " char left");
             tvCharacterCount.setTextColor(140 - composeText.length() > 0 ? Color.GRAY : Color.RED);
             setEnableTweet(140 - composeText.length() > 0);
            }
        });

    }

    private void setEnableTweet(boolean enabled) {
        enableTweet = enabled;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#89cff0")));
        return super.onCreateOptionsMenu(menu);
    }

    private void composeTweet() {
        if (!enableTweet) { return; }
        setEnableTweet(false);
        client.postTweet(composeText.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Tweet newTweet = null;

                try {
                    newTweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent();
                i.putExtra("newTweet", Parcels.wrap(newTweet));
                setResult(COMPOSE_RESULT_CODE, i);
                finish();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                setEnableTweet(true);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),"Composing...", Toast.LENGTH_SHORT).show();

        switch(item.getItemId()) {
            case R.id.composeOption:
                String title = getSupportActionBar().getTitle().toString();
                getSupportActionBar().setTitle("Composing.....");
                composeTweet();
                getSupportActionBar().setTitle(title);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
