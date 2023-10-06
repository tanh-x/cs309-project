package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StudentInfo extends AppCompatActivity {

    private static final String URL_STRING_REQ = "http://cs309.kewargs.com:8080/api/user/username/student2";
    Button getI, back;
    TextView res;

    EditText uid,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_info);


        uid = (EditText) findViewById(R.id.uid);
        username= (EditText) findViewById(R.id.username);

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

                String userID = uid.getText().toString();
                String uName = username.getText().toString();

                if (userID.length()>0&&uName.length()>0)
                {
                    makeStringReq();
                }
                else
                {
                    res.setText("Missing Fields!");
                }
            }
        });

    }
    private void showToast(String text)
    {
        Toast.makeText(StudentInfo.this,text,Toast.LENGTH_SHORT).show();
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
