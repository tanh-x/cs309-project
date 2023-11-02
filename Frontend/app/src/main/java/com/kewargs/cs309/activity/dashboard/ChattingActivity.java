package com.kewargs.cs309.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import org.json.*;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.manager.WebSocketManager;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;
import com.kewargs.cs309.core.utils.backend.request.WebSocketListener;

import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import org.json.JSONException;

public class ChattingActivity extends AbstractActivity implements WebSocketListener {
    public ChattingActivity() { super(R.layout.chatting); }

    private Button backBtn, sendBtn;

    private String username;
    private EditText msgEtx;
    private TextView msgTv;
    UserDeserializable userInfo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session.addRequest(UserRequestFactory
                .getUserById(session.getUserId())
                .onResponse(response -> {
                    try {
                        userInfo = UserDeserializable.from(response);
                        username = userInfo.email();
                    } catch (JSONException ignored) { }
                })
                .bearer(session.getSessionToken())
                .build()
        );

        String serverUrl = "ws://coms-309-029.class.las.iastate.edu:8080/chat/" + username;
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(ChattingActivity.this);

        sendBtn.setOnClickListener(v -> {
        try {
            WebSocketManager.getInstance().sendMessage(msgEtx.getText().toString());
        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }
        });
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
        sendBtn = findViewById(R.id.SendText);
        msgEtx = findViewById(R.id.TextBox);
    }

    @Override
    public void onWebSocketMessage(String message) {
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
