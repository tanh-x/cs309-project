package com.example.two_screen_switch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;
    private Button loginButton;

    // Retrofit and ApiService variables
    private Retrofit retrofit;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://yourapi.com/")  // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())  // OkHttpClient can be customized as needed
                .build();

        // Initialize ApiService
        apiService = retrofit.create(ApiService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the entered ID and password
                String id = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();

                // Create a login request using Retrofit
                Call<LoginResponse> call = apiService.login(id, password);

                // Perform the login request asynchronously
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle a successful login response from the server
                            LoginResponse loginResponse = response.body();
                            // Perform necessary actions after a successful login
                            showToast("Login successful!");
                        } else {
                            // Handle an unsuccessful login response from the server
                            // You can check the response code and show appropriate messages to the user
                            showToast("Invalid ID or password. Please try again.");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle network or server errors here
                        // You can display an error message to the user
                        showToast("Login failed. Please check your network connection.");
                    }
                });
            }
        });
    }

    // Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
