package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import org.json.*;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

public class DashboardActivity extends AbstractActivity {
    public DashboardActivity() { super(R.layout.activity_dashboard); }

    private TextView userInfoDump;
    private TextView dashboardGreeting;
    private Button updateInfo;
    private Button coursesButton;

    UserDeserializable userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        session.addRequest(UserRequestFactory
            .getUserById(session.getUserId())
            .onResponse(response -> {
                userInfoDump.setText(response);
                try {
                    userInfo = UserDeserializable.from(response);
                    dashboardGreeting.setText("Hello " + userInfo.displayName());
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

    private void coursesButtonCallback(View view) {
        Intent intent = new Intent(DashboardActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void collectElements() {
        userInfoDump = findViewById(R.id.userInfoDump);
        dashboardGreeting = findViewById(R.id.dashboardGreeting);
        updateInfo = findViewById(R.id.updateInfo);
        coursesButton = findViewById(R.id.coursesButton);
    }
}
