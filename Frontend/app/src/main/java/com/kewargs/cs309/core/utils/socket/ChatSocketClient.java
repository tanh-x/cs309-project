package com.kewargs.cs309.core.utils.socket;

import static com.kewargs.cs309.core.utils.constants.UniversalConstants.CHAT_ENDPOINT;

import com.kewargs.cs309.core.managers.SessionManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

/**
 * A WebSocket client dedicated to chat functionality. It handles the setup and management of a WebSocket
 * connection for chatting, including opening the connection, handling messages, and closing the connection.
 * It uses a generic type S for a listener that handles the WebSocket events and actions.
 *
 * @param <S> The type of the listener that will handle WebSocket events for this client.
 */
public final class ChatSocketClient<S extends SocketListener> extends WebSocketClient {
    private final SessionManager session;
    private final S listener;

    /**
     * Constructs a ChatSocketClient using session information to authenticate and initialize connection endpoint.
     *
     * @param session  The session manager holding authentication token and user details.
     * @param listener The listener that will handle WebSocket events.
     */
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

    /**
     * Callback method that is invoked when the WebSocket connection is successfully opened.
     *
     * @param handshakedata The handshake data from the server.
     */
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        listener.onSocketOpen(handshakedata);
    }

    /**
     * Callback method that is invoked when a message is received from the server.
     *
     * @param message The message received from the server.
     */
    @Override
    public void onMessage(String message) {
        listener.onSocketMessage(message);
    }

    /**
     * Callback method that is invoked when the WebSocket connection is closed.
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason The string explanation of why the connection closed.
     * @param remote Indicates if the closure was initiated by the remote host.
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        listener.onSocketClose(code, reason, remote);
    }

    /**
     * Callback method that is invoked when an error occurs on the WebSocket connection.
     *
     * @param ex The exception representing the error that occurred.
     */
    @Override
    public void onError(Exception ex) {
        listener.onSocketError(ex);
    }
}
