package com.example.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RequestSVActivity extends AppCompatActivity {
    private TextView RQSVTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestsv);
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        // Create GET by Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // JSON here
                        try {
                            //String userID = response.getString("userID");
                            //String id = response.getString("id");
                            String title = response.getString("title");
                            String body = response.getString("body");
                            //String result = "UserID:"+userID +"\nID"+ id +"\nTitle: " + title + "\nBody: " + body;
                            String result = "Title: " + title + "\nBody: " + body;

                            RQSVTextView.setText(result);
                        } catch (Exception e) {
                            RQSVTextView.setText("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                        RQSVTextView.setText("Error: " + error.getMessage());
                    }
                });

        // add requesst Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);

        //set up back login
        Button backlogrq_in_Button = findViewById(R.id.backlogrq_in_Button);
        backlogrq_in_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to back Login
                Intent intent = new Intent(RequestSVActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}