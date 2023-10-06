package com.example.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class JsonTestActivity extends AppCompatActivity {

    private TextView jsonResultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_json);
        // event json

        jsonResultTextView = findViewById(R.id.jsonResultTextView);

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

                            jsonResultTextView.setText(result);
                        } catch (Exception e) {
                            jsonResultTextView.setText("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi ở đây
                        jsonResultTextView.setText("Error: " + error.getMessage());
                    }
                });

        // add requesst Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        //set up back login
        Button backlog_in_Button = findViewById(R.id.backlog_in_Button);
        backlog_in_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to back Login
                Intent intent = new Intent(JsonTestActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
