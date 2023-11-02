package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.*;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;

import org.json.JSONException;

public class ChattingActivity extends AbstractActivity {
    public ChattingActivity() { super(R.layout.chatting); }

    Button backBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backBtn.setOnClickListener(this::backBtnCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void backBtnCallback(View view) {
        switchToActivity(DashboardActivity.class);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(ChattingActivity.this, newActivity);
        startActivity(intent);
    }

    @Override
    protected void collectElements() {
        backBtn = findViewById(R.id.backDash);
    }
}
