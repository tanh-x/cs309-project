package com.kewargs.cs309.activity.auth;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static com.kewargs.cs309.core.utils.ElementHelpers.parse;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.dashboard.DashboardActivity;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;
import com.kewargs.cs309.core.utils.constants.UniversalConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashSet;
/**
 * The first screen that the user is prompted with if they aren't logged in.
 * The user password is encrypted.
 *
 * @author Thanh Mai
 */
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
        if (session != null && session.isLoggedIn()) switchToActivity(DashboardActivity.class);

        super.onCreate(savedInstanceState);

        if (forgotPasswordLink != null) forgotPasswordLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(this::loginButtonCallback);
        registerButton.setOnClickListener(this::registerButtonCallback);

        passwordField.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                loginButtonCallback(v);
                return true;
            }
            return false;
        });

        try {
            debugInfoText.setText(String.format("Current backend address: %s", UniversalConstants.ENDPOINT));
        } catch (ExceptionInInitializerError e) {
            showToast("Can't reach server", this);
        }
    }

    private void loginButtonCallback(View view) {
        formElements.forEach(v -> v.setEnabled(false));

        String emailValue = parse(emailField);
        String passwordValue = parse(passwordField);
        Request<String> request = UserRequestFactory
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

    private void onSuccessfulLogin(String response) {
        try {
            JSONObject deserializedResponse = new JSONObject(response);
            showToast("Successfully logged in!");
            session.setSessionFromLogin(deserializedResponse);
            switchToActivity(DashboardActivity.class);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(this, newActivity);
        startActivity(intent);
    }


    private void showToast(String content) {
        AbstractActivity.showToast(content, this);
    }

    @Override
    protected void collectElements() {
        emailField = findViewById(R.id.newname);
        passwordField = findViewById(R.id.newDisplay);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        debugInfoText = findViewById(R.id.debugInfoText);

        formElements = new LinkedHashSet<>() {{
            add(emailField);
            add(passwordField);
            add(loginButton);
            add(registerButton);
        }};
    }
}
