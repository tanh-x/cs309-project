package com.kewargs.cs309.activity.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.utils.socket.ChatSocketClient;
import com.kewargs.cs309.core.utils.socket.SocketListener;

import org.java_websocket.handshake.ServerHandshake;

@SuppressLint("SetTextI18n")
public class ChatActivity extends AbstractActivity implements SocketListener {
    public ChatActivity() { super(R.layout.activity_chat); }

    private ChatSocketClient chatSocketClient;

    private Button backButton;
    private Button sendButton;
    private EditText messageTextBox;
    private TextView chatText;

    private ScrollView scroller; //perma scroll


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendButton.setOnClickListener(this::sendButtonCallback);
        backButton.setOnClickListener(this::backBtnCallback);
        scroller.fullScroll(ScrollView.FOCUS_DOWN);

        chatSocketClient = new ChatSocketClient<>(session, this);
        chatSocketClient.connect();
    }

    private void sendButtonCallback(View view) {
        try {
            chatSocketClient.send(messageTextBox.getText().toString());
            scroller.fullScroll(ScrollView.FOCUS_DOWN);
            messageTextBox.setText("");
        } catch (Exception ex) {
            showToast(ex.toString(), this);
        }
    }

    private void backBtnCallback(View view) {
        chatSocketClient.close();
        finish();
    }

    @Override
    protected void collectElements() {
        backButton = findViewById(R.id.backDash);
        sendButton = findViewById(R.id.sendText);
        messageTextBox = findViewById(R.id.messageTextBox);
        chatText = findViewById(R.id.chatText);
        scroller = findViewById(R.id.scrollView2);
    }

    @Override
    public void onSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = chatText.getText().toString();
            chatText.setText(s + "\n" + message);
            Log.i("ChatActivity", message);
            scroller.fullScroll(ScrollView.FOCUS_DOWN);
        });

    }

    @Override
    public void onSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = chatText.getText().toString();
            chatText.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onSocketOpen(ServerHandshake handshake) {
        runOnUiThread(() -> {
            showToast("Connected to chat", this);
        });
    }

    @Override
    public void onSocketError(Exception ex) {
        runOnUiThread(() -> {
            showToast("Socket error: " + ex.toString(), this);
        });
    }
}
