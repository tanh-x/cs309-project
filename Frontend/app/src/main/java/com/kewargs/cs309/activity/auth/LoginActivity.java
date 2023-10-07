package com.kewargs.cs309.activity.auth;

import static com.kewargs.cs309.utils.ElementHelpers.parse;
import static com.kewargs.cs309.utils.UniversalConstants.USER_ENDPOINT;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.dashboard.DashboardActivity;
import com.kewargs.cs309.core.activity.AbstractActivity;
import com.kewargs.cs309.utils.backend.RequestFactory;

import org.json.JSONException;

import java.util.LinkedHashSet;

public class LoginActivity extends AbstractActivity {
    public LoginActivity() { super(R.layout.activity_login); }

    private EditText emailField;
    private EditText passwordField;
    private TextView forgotPasswordLink;
    private Button loginButton;
    private Button registerButton;

    /**
     * @noinspection MismatchedQueryAndUpdateOfCollection
     */
    private LinkedHashSet<View> loginFormElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (session.isLoggedIn()) switchToActivity(DashboardActivity.class);

        super.onCreate(savedInstanceState);
        if (forgotPasswordLink != null) forgotPasswordLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        loginButton.setOnClickListener(this::loginButtonCallback);
        registerButton.setOnClickListener(this::registerButtonCallback);
    }

    private void loginButtonCallback(View view) {
        loginFormElements.forEach(v -> v.setEnabled(false));

        try {
            JsonObjectRequest request = RequestFactory
                .POST()
                .url(USER_ENDPOINT + "login")
                .putBody("email", parse(emailField))
                .putBody("password", parse(passwordField))
                .onResponse(response -> {
                    showToast("Successfully logged in: " + response);
                    session.setSessionFromLogin(response);
                    switchToActivity(DashboardActivity.class);
                })
                .onError(error -> {
                    error.printStackTrace();
                    showToast(error.toString());
                })
                .build();

            session.addRequest(request);
        } catch (JSONException ignored) {
            // i dont give a swag bro
        } finally {
            loginFormElements.forEach(v -> v.setEnabled(true));
        }
    }

    private void registerButtonCallback(View view) {
        switchToActivity(RegisterActivity.class);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(LoginActivity.this, newActivity);
        startActivity(intent);
    }


    private void showToast(String content) {
        AbstractActivity.showToast(content, LoginActivity.this);
    }

    @Override
    protected void collectElements() {
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginFormElements = new LinkedHashSet<View>() {{
            add(emailField);
            add(passwordField);
            add(loginButton);
            add(registerButton);
        }};
    }
}
