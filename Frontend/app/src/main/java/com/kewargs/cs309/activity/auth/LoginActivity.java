package com.kewargs.cs309.activity.auth;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.core.activity.AbstractActivity;

import java.util.LinkedHashSet;

public class LoginActivity extends AbstractActivity {
    public LoginActivity() { super(R.layout.activity_login); }

    private EditText emailField;
    private EditText passwordField;
    private TextView forgotPasswordLink;
    private Button loginButton;
    private Button registerButton;

    private LinkedHashSet<View> loginFormElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (forgotPasswordLink != null) forgotPasswordLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(this::loginButtonCallback);
        registerButton.setOnClickListener(this::registerButtonCallback);
    }

    private void loginButtonCallback(View view) {
        loginFormElements.forEach(v -> v.setEnabled(false));

    }

    private void registerButtonCallback(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        startActivity(intent);
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
