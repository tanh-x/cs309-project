package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import org.json.*;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;
import com.kewargs.cs309.core.utils.backend.request.WebSocketListener;

import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import org.json.JSONException;

public class ChattingActivity extends AbstractActivity implements WebSocketListener {
    public ChattingActivity() { super(R.layout.chatting); }

    private Button backBtn, connectBtn, sendBtn;
    private EditText usernameEtx, msgEtx;
    private TextView msgTv;
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

    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "\n"+message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = msgTv.getText().toString();
            msgTv.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}
