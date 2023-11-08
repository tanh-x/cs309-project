package com.kewargs.cs309.activity.dashboard;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.AUDIT_UPLOAD_ENDPOINT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.android.volley.Request;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.utils.backend.request.MultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;



public class AuditUploadActivity extends AbstractActivity {
    public AuditUploadActivity() {
        super(R.layout.activity_pdf_uploader);
    }

    private Button backDash, auditUploadButton;

    ActivityResultLauncher<Intent> auditUploadResultLaunder; //why google

    private String upload_URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/uploadfile.php?";

    private TextView tv;

    String url = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backDash.setOnClickListener(this::backDashCallback);
        auditUploadButton.setOnClickListener(this::uploadAuditCallback);
        auditUploadResultLaunder = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();
                //doSomeOperations();
            }
        });

        //ill refactor later trust
        tv.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

    }

    private void backDashCallback(View view) { switchToActivity(DashboardActivity.class); }

    private void uploadAuditCallback(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        auditUploadResultLaunder.launch(intent);
    }

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(AuditUploadActivity.this, newActivity);
        startActivity(intent);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//im sorry
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            // tv.setText(uriString+"\n"+path); //file location works till here :)
            String displayName = null;

            Log.i("AuditUploadActivity", uriString);
            if (uriString.startsWith("content://")) {
                try (Cursor cursor = this.getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor == null || !cursor.moveToFirst()) {
                        throw new RuntimeException("Cursor error");
                    }

                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.d("AuditUploadActivity", displayName);

                    uploadPDF(displayName, uri);
                }
            } else if (uriString.startsWith("file://")) {
                throw new UnsupportedOperationException("Later");
//                displayName = myFile.getName();
//                Log.d("nameeeee>>>>  ", displayName); //dont bother
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfName, Uri pdfFileUri) {
        try (InputStream inputStream = getContentResolver().openInputStream(pdfFileUri)) {
            if (inputStream == null) throw new RuntimeException("inputStream is null");

            final byte[] inputData = getBytes(inputStream);

            MultipartRequest multipartRequest = new MultipartRequest(
                Request.Method.POST,
                AUDIT_UPLOAD_ENDPOINT,
                pdfName,
                inputData,
                session.getSessionToken(),
                response -> {
                    tv.setText("Completed courses:\n" +parseOutpust(response));
                    Log.d("Upload", "Response: " + response);
                },
                error -> {
                    showToast(error.toString(), this);
                    Log.e("Upload", error.toString());
                }
            );
            session.addRequest(multipartRequest);

        } catch (Exception e) {
            showToast(e.toString(), this);
        }
    }

    public String parseOutpust(String res)
    {
        List<String> returnList = null;
        res = res.replaceAll("[\\[\\]]", "");
        returnList = Arrays.asList(res.split(", "));
        String f= "";
        for(String i:returnList)
        {
            f+=i+"\n";
        }
        return f;
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void collectElements() {
        backDash = findViewById(R.id.backDashboard);
        auditUploadButton = findViewById((R.id.auditUpload));
        tv = findViewById(R.id.pdfUrl);
    }

}
