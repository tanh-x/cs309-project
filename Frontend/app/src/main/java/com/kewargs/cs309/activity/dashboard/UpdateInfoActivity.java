package com.kewargs.cs309.activity.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import org.json.*;
import com.kewargs.cs309.R;
import com.kewargs.cs309.core.activity.AbstractActivity;
import com.kewargs.cs309.utils.backend.factory.UserRequestFactory;

public class UpdateInfoActivity extends AbstractActivity {
    public UpdateInfoActivity() { super(R.layout.activity_update_info); }

    private Button dashBack, confirmUpdate;

    private EditText newUser, newEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        dashBack.setOnClickListener(this::dashBackButtonCallBack);

    }
    private void dashBackButtonCallBack(View view) {
        switchToActivity(DashboardActivity.class);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(UpdateInfoActivity.this, newActivity);
        startActivity(intent);
    }

    protected void collectElements() {
        dashBack = findViewById(R.id.updateBack);
    }





}
