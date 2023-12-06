package com.kewargs.cs309.activity.dashboard;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kewargs.cs309.MainActivity;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

import org.json.JSONException;
public class SchedulingActivity extends AbstractActivity{

    private Button backDash;
    public SchedulingActivity() {super(R.layout.schedule_display);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backDash.setOnClickListener(this::toDashBoardCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void toDashBoardCallback(View view) {
        switchToActivity(DashboardActivity.class);
    }
    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(SchedulingActivity.this, newActivity);
        startActivity(intent);
    }

    @Override
    protected void collectElements() {
        backDash = findViewById(R.id.backDashSched);
    }
}
