package com.kewargs.cs309.activity.dashboard;


import static com.kewargs.cs309.core.utils.ElementHelpers.parse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

import java.util.LinkedHashSet;


public class UpdateInfoActivity extends AbstractActivity {
    public UpdateInfoActivity() { super(R.layout.activity_update_info); }

    private Button dashBack, confirmUpdate;

    private EditText newUser, newEmail;

    private LinkedHashSet<View> formElements;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashBack.setOnClickListener(this::dashBackButtonCallBack);
        confirmUpdate.setOnClickListener(this::updateConfirmationCallback);

    }

    private void dashBackButtonCallBack(View view) {
        switchToActivity(DashboardActivity.class);
    }

    private void updateConfirmationCallback(View view) {
        formElements.forEach(v -> v.setEnabled(false));

        String nEmail = parse(newEmail);
        String newDisplayName = parse(newUser);
        Request<String> request = UserRequestFactory
            .updateInfo(session.getUserId(), nEmail, newDisplayName)
            .onResponse(response -> {
                showToast("Successfully updated information", UpdateInfoActivity.this);
                switchToActivity(DashboardActivity.class);
            })
            .onError(error -> {
                error.printStackTrace();
                showToast(error.toString(), UpdateInfoActivity.this);
            })
            .build();

        session.addRequest(request);

        formElements.forEach(v -> v.setEnabled(true));
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(UpdateInfoActivity.this, newActivity);
        startActivity(intent);
    }

    protected void collectElements() {

        dashBack = findViewById(R.id.updateBack);
        confirmUpdate = findViewById(R.id.updateConfirmation);
        newUser = findViewById(R.id.newDisplay);
        newEmail = findViewById(R.id.newname);

        formElements = new LinkedHashSet<>() {{
            add(newUser);
            add(newEmail);
        }};
    }
}
