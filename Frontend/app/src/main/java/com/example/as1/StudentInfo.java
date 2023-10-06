package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StudentInfo extends AppCompatActivity {

    //private static final String URL_STRING_REQ = "http://cs309.kewargs.com:8080/api/user/username/student2";
    Button getI, back;
    TextView res;
    int num;
    EditText userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info);

        back = findViewById(R.id.backInfo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getI = (Button) findViewById(R.id.getInfo);
        res = (TextView) findViewById(R.id.res);

        getI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt(userId.getText().toString());
                String URL_STRING_REQ = "http://cs309.kewargs.com:8080/api/user/email"+;
                makeStringReq();

            }
        });

    }

    private void makeStringReq() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the successful response here
                        Log.d("Volley Response", response);
                        res.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        res.setText("Error");
                        Log.e("Volley Error", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("param1", "value1");
                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
