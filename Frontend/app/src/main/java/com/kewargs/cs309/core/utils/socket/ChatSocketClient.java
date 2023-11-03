package com.kewargs.cs309.core.utils.socket;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.CHAT_ENDPOINT;

import com.kewargs.cs309.core.managers.SessionManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

public final class ChatSocketClient<S extends SocketListener> extends WebSocketClient {
    private final SessionManager session;
    private final S listener;

    public ChatSocketClient(SessionManager session, S listener) {
        super(
            URI.create(CHAT_ENDPOINT + session.getUsername()),
            new HashMap<>() {{
                put("Authorization", "Bearer " + session.getSessionToken());
            }}
        );

        this.session = session;
        this.listener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        listener.onSocketOpen(handshakedata);
    }

    @Override
    public void onMessage(String message) {
        listener.onSocketMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        listener.onSocketClose(code, reason, remote);
    }


    @Override
    public void onError(Exception ex) {
        listener.onSocketError(ex);
    }
}
