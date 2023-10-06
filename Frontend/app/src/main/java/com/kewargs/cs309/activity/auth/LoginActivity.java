package com.kewargs.cs309.activity.auth;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.core.AbstractActivity;

public class LoginActivity extends AbstractActivity {
    public LoginActivity() { super(R.layout.activity_login); }

    private EditText emailField;
    private EditText passwordField;
    private TextView forgotPasswordLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (forgotPasswordLink != null) forgotPasswordLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    @Override
    protected void collectElements() {
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
    }
}
