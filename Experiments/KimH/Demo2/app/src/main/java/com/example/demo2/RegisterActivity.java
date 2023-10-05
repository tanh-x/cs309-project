package com.example.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserID, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUserID = findViewById(R.id.etUserID);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input from EditText fields
                String fullName = etUserID.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                //

                registerUser();
                // Perform registration logic here
                if (isValidRegistration(fullName, email, password, confirmPassword)) {
                    // Registration is valid, you can save the user's data or perform other actions
                } else {
                    // Registration is not valid, show an error message or handle it accordingly
                }
            }
        });
        Button button_RegistertoLogin = findViewById(R.id.registerToLoginButton);
        button_RegistertoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Register
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validate the registration input
    private boolean isValidRegistration(String fullName, String email, String password, String confirmPassword) {
        // Implement your validation logic here
        // Check if the input is valid, e.g., email format, password strength, matching passwords, etc.
        return true; // Return true if registration is valid, otherwise return false
    }


    //
    private void registerUser() {
        String url = "YOUR_SERVER_URL/register"; // Thay YOUR_SERVER_URL bằng URL của máy chủ đăng ký

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("username", etUserID.getText().toString());
            requestData.put("password", etPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // XCheck result of Register
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // if success
                            } else {
                                //
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                });

        requestQueue.add(jsonRequest);
    }

}
