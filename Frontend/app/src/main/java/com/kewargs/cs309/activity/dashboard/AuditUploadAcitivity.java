package com.kewargs.cs309.activity.dashboard;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;

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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import org.json.*;

import com.kewargs.cs309.core.utils.backend.request.VolleyMultipartRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AuditUploadAcitivity extends AbstractActivity {
    public AuditUploadAcitivity() {
        super(R.layout.activity_pdf_uploader);
    }

    Button backDash, auditUploadButton;

    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;

    ActivityResultLauncher<Intent> auditUploadResultLaunder; //why google

    private String upload_URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/uploadfile.php?";

    private TextView tv;

    String url = "https://www.google.com";

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

        tv.setOnClickListener(new View.OnClickListener() { //ill refactor later trust
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
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

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        //uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("response",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                showToast(jsonObject.getString("message"),AuditUploadAcitivity.this);

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString("status").equals("true")) {
                                    Log.d("come::: >>>  ","yessssss");
                                    arraylist = new ArrayList<HashMap<String, String>>();
                                    JSONArray dataArray = jsonObject.getJSONArray("data");


                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        url = dataobj.optString("pathToFile");
                                        tv.setText(url); //textview
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast(error.getMessage(),AuditUploadAcitivity.this);
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // params.put("tags", "ccccc");  add string parameters
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            //rQueue = Volley.newRequestQueue(AuditUploadAcitivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    protected void collectElements() {
        backDash =findViewById(R.id.backDashboard);
        auditUploadButton = findViewById((R.id.auditUpload));
        tv = findViewById(R.id.pdfUrl);
    }

}
