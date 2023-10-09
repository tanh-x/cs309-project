package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import org.json.*;
import com.kewargs.cs309.R;
import com.kewargs.cs309.core.activity.AbstractActivity;
import com.kewargs.cs309.utils.backend.factory.UserRequestFactory;

public class DashboardActivity extends AbstractActivity {
    public DashboardActivity() { super(R.layout.activity_dashboard); }

    private TextView userInfoDump;
    private TextView dashboardGreeting;
    private Button updateInfo;
    JSONObject userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session.addRequest(UserRequestFactory
            .getUserById(session.getUserId())
            .onResponse(response -> {
                userInfoDump.setText(response);
                try {
                    userInfo = new JSONObject(response);
                    dashboardGreeting.setText("Hello " + userInfo.getString("displayName"));
                } catch (JSONException ignored) { }

            })
            .build()
        );

        updateInfo.setOnClickListener(this::updateInfoCallback);

    }
    private void updateInfoCallback(View view) {
        switchToActivity(UpdateInfoActivity.class);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(DashboardActivity.this, newActivity);
        startActivity(intent);
    }

    @Override
    protected void collectElements() {
        userInfoDump = findViewById(R.id.userInfoDump);
        dashboardGreeting = findViewById(R.id.dashboardGreeting);
        updateInfo = findViewById(R.id.updateInfo);
    }




}
