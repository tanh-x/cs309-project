package com.example.demo2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText user_name;
    private EditText user_password;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);
        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user ID and password from input
                String username = user_name.getText().toString();
                String password = user_password.getText().toString();

                // Call a function to validate login using an API
                validateLogin(username, password);
            }
        });
        //button
        Button button_Register = findViewById(R.id.button_register);
        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Register
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //buttonjsontest
        Button button_JsonTest = findViewById(R.id.button_jsontest);
        button_JsonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Jsontest
                Intent intent = new Intent(LoginActivity.this, JsonTestActivity.class);
                startActivity(intent);
            }
        });
        Button button_RequestSV = findViewById(R.id.button_RequestSV);
        button_JsonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Jsontest
                Intent intent = new Intent(LoginActivity.this, RequestSVActivity.class);
                startActivity(intent);
            }
        });
    }

    // Function to validate login using an API
    private void validateLogin(String username, String password) {
        // Define the API endpoint where you can validate the login
        //String apiUrl = "https://8304f33b-fccf-4bea-b375-70ce054820da.mock.pstmn.io/api/login"; // Replace with your API endpoint
            String apiUrl ="http://cs309.kewargs.com:8080/api/user/username";
                // Create a JSON object to hold user data
        JSONObject userData = new JSONObject();
        try {
            userData.put("username", username);
            userData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JSON request to send user data to the API
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                apiUrl,
                userData,
                response -> {
                    // Handle the API response here
                    try {
                        boolean loginSuccessful = response.getBoolean("success");

                        if (loginSuccessful) {
                            // Login successful, navigate to MainActivity
                            Intent intent = new Intent(LoginActivity.this, RequestSVActivity.class);
                            startActivity(intent);
                        } else {
                            // Login failed, display an error message
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle network error
                    Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }
}
