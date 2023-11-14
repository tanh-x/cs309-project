package com.kewargs.cs309.activity.auth;

import static com.kewargs.cs309.core.utils.ElementHelpers.parse;
import static com.kewargs.cs309.core.utils.constants.UniversalConstants.AUTH_ENDPOINT;
import static com.kewargs.cs309.core.utils.constants.UniversalConstants.USER_ENDPOINT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.utils.backend.factory.RequestFactory;

import org.json.JSONException;

import java.util.LinkedHashSet;

public class RegisterActivity extends AbstractActivity
    implements AdapterView.OnItemSelectedListener {
    public RegisterActivity() {
        super(R.layout.activity_register);
    }

    private EditText nameField;
    private EditText emailField;
    private EditText usernameField;
    private EditText passwordField;
    private Spinner accountTypeSpinner;
    private Button signUpButton;
    private Button loginButton;

    private int accountTypeValue;

    /**
     * @noinspection MismatchedQueryAndUpdateOfCollection
     */
    private LinkedHashSet<View> formElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpButton.setOnClickListener(this::signUpButtonCallback);
        loginButton.setOnClickListener(this::loginButtonCallback);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(
            this, R.array.privilege_array,
            android.R.layout.simple_spinner_item
        );
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(staticAdapter);
        accountTypeSpinner.setOnItemSelectedListener(this);
    }

    private void signUpButtonCallback(View view) {
        String username = parse(usernameField);
        String email = parse(emailField);
        String password = parse(passwordField);
        String displayName = parse(nameField);

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
            showToast("Please complete the registration form first", RegisterActivity.this);
            return;
        }

        formElements.forEach(v -> v.setEnabled(false));

        try {
            Request<String> request = RequestFactory.POST()
                .url(AUTH_ENDPOINT + "register")
                .putBody("username", username)
                .putBody("email", email)
                .putBody("password", password)
                .putBody("displayName", displayName)
                .putBody("privilegeLevel", "" + accountTypeValue)
                .onResponse(response -> {
                    showToast("Registration was successful!", RegisterActivity.this);
                    switchToActivity(LoginActivity.class);
                })
                .onError(error -> {
                    if (error.networkResponse.statusCode == 500) {
                        showToast(
                            "Registration failed, possibly due to duplicate credentials",
                            RegisterActivity.this
                        );
                    } else {
                        error.printStackTrace();
                        showToast(error.toString(), RegisterActivity.this);
                    }
                })
                .build();

            session.addRequest(request);
        } catch (JSONException ignored) { }

        formElements.forEach(v -> v.setEnabled(true));
    }

    private void loginButtonCallback(View view) {
        switchToActivity(LoginActivity.class);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(RegisterActivity.this, newActivity);
        startActivity(intent);
    }

    @Override
    protected void collectElements() {
        nameField = findViewById(R.id.newname);
        emailField = findViewById(R.id.emailField);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.newDisplay);
        accountTypeSpinner = findViewById(R.id.accountTypeSpinner);
        signUpButton = findViewById(R.id.coursesButton);
        loginButton = findViewById(R.id.loginButton);

        formElements = new LinkedHashSet<>() {{
            add(nameField);
            add(emailField);
            add(usernameField);
            add(passwordField);
            add(accountTypeSpinner);
            add(signUpButton);
            add(loginButton);
        }};
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch ((int) id) {
            case 0 -> accountTypeValue = 1;
            case 1 -> accountTypeValue = 2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
