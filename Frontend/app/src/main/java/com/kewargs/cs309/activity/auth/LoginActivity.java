package com.kewargs.cs309.activity.auth;

import static com.kewargs.cs309.utils.ElementHelpers.parse;

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
import com.kewargs.cs309.utils.backend.factory.UserRequestFactory;
import com.kewargs.cs309.utils.constants.UniversalConstants;

import org.json.JSONObject;

import java.util.LinkedHashSet;

public class LoginActivity extends AbstractActivity {
    public LoginActivity() { super(R.layout.activity_login); }

    private EditText emailField;
    private EditText passwordField;
    private TextView forgotPasswordLink;
    private Button loginButton;
    private Button registerButton;
    private TextView debugInfoText;

    /**
     * @noinspection MismatchedQueryAndUpdateOfCollection
     */
    private LinkedHashSet<View> formElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (session.isLoggedIn()) switchToActivity(DashboardActivity.class);

        super.onCreate(savedInstanceState);

        if (forgotPasswordLink != null) forgotPasswordLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(this::loginButtonCallback);
        registerButton.setOnClickListener(this::registerButtonCallback);

        debugInfoText.setText(String.format("Current backend address: %s", UniversalConstants.ENDPOINT));
    }

    private void loginButtonCallback(View view) {
        formElements.forEach(v -> v.setEnabled(false));

        String emailValue = parse(emailField);
        String passwordValue = parse(passwordField);
        JsonObjectRequest request = UserRequestFactory
            .login(emailValue, passwordValue)
            .onResponse(this::onSuccessfulLogin)
            .onError(error -> {
                error.printStackTrace();
                showToast(error.toString());
            })
            .build();

        session.addRequest(request);

        formElements.forEach(v -> v.setEnabled(true));
    }


    private void registerButtonCallback(View view) {
        switchToActivity(RegisterActivity.class);
    }

    private void onSuccessfulLogin(JSONObject response) {
        showToast("Successfully logged in!");
        session.setSessionFromLogin(response);
        switchToActivity(DashboardActivity.class);
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
        emailField = findViewById(R.id.nameField);
        passwordField = findViewById(R.id.passwordField);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        loginButton = findViewById(R.id.signUpButton);
        registerButton = findViewById(R.id.loginButton);
        debugInfoText = findViewById(R.id.debugInfoText);

        formElements = new LinkedHashSet<View>() {{
            add(emailField);
            add(passwordField);
            add(loginButton);
            add(registerButton);
        }};
    }
}
