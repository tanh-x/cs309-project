package cs309.backend.controllers;

import java.io.IOException;

import cs309.backend.Component.SessionStore;

import cs309.backend.config.CustomConfigurator;
import cs309.backend.services.MessageService;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/{username}", configurator = CustomConfigurator.class)
public class ChatSocketController {
    private final Logger logger = LoggerFactory.getLogger(ChatSocketController.class);
    private final SessionStore sessionStore;
    private final MessageService messageService;

    /**
     * Constructor for ChatSocketController.
     *
     * @param messageService The service responsible for handling chat messages.
     * @param sessionStore   The store for managing WebSocket sessions.
     */
    @Autowired
    public ChatSocketController(MessageService messageService, SessionStore sessionStore) {
        this.messageService = messageService;
        this.sessionStore = sessionStore;
    }

    /**
     * Method called when a new WebSocket connection is opened.
     *
     * @param session  The WebSocket session representing the connection.
     * @param username The username associated with the connection.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        logger.info("Entered into Open");
        messageService.handleOpenSession(session, username);
        messageService.broadcastJoinMessage(username);
    }

    /**
     * Method called when a message is received from a WebSocket connection.
     *
     * @param session The WebSocket session representing the connection.
     * @param message The message received from the connection.
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("Entered into Message: Got Message:" + message);
        messageService.handleIncomingMessage(session, message);
    }

    /**
     * Method called when a WebSocket connection is closed.
     *
     * @param session The WebSocket session representing the connection.
     */
    @OnClose
    public void onClose(Session session) {
        logger.info("Entered into Close");
        messageService.handleCloseSession(session);
    }

    /**
     * Method called when an error occurs in a WebSocket connection.
     *
     * @param session   The WebSocket session representing the connection.
     * @param throwable The throwable representing the encountered error.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        logger.error("Error encountered", throwable);
    }
}

