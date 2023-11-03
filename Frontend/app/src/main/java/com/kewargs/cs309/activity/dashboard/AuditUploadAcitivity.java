package com.kewargs.cs309.activity.dashboard;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import org.json.*;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.auth.LoginActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;


public class AuditUploadAcitivity extends AbstractActivity {
    public AuditUploadAcitivity() {
        super(R.layout.activity_pdf_uploader);
    }

    Button backDash, auditUploadButton;

    ActivityResultLauncher<Intent> auditUploadResultLaunder; //why google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backDash.setOnClickListener(this::backDashCallback);
        auditUploadButton.setOnClickListener(this::uploadAuditCallback);
        auditUploadResultLaunder = registerForActivityResult( //idk
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            //doSomeOperations();
                        }
                    }
                });

    }

    private void backDashCallback(View view) {switchToActivity(DashboardActivity.class);}

    private void uploadAuditCallback(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        auditUploadResultLaunder.launch(intent);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(AuditUploadAcitivity.this, newActivity);
        startActivity(intent);
    }
    @Override
    protected void collectElements() {
        backDash =findViewById(R.id.backDashboard);
        auditUploadButton = findViewById((R.id.auditUpload));
    }

}
