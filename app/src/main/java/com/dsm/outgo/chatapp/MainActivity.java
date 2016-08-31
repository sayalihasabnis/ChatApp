package com.dsm.outgo.chatapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    EditText messageInput;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get our input field by its ID
        messageInput = (EditText) findViewById(R.id.message_input);


        // get our button by its ID
        sendButton = (Button) findViewById(R.id.send_button);

        // set its click listener
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        postMessage();
    }
    private void postMessage() {
        String text = messageInput.getText().toString();

        // return if the text is blank
        if (text.equals("")) {
            return;
        }
        RequestParams params = new RequestParams();

        // set our JSON object
        params.put("text", text);
        // params.put("name", username);
        params.put("time", new Date().getTime());

        // create our HTTP client
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(MESSAGES_ENDPOINT, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageInput.setText("");
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(
                        getApplicationContext(),
                        "Something went wrong :(",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    }