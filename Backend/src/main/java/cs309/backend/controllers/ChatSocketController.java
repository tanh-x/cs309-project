package cs309.backend.controllers;

import java.io.IOException;

import cs309.backend.Component.SessionStore;

import cs309.backend.config.CustomConfigurator;
import cs309.backend.jpa.entity.MessageEntity;
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


    @Autowired
    public ChatSocketController(MessageService messageService, SessionStore sessionStore) {
        this.messageService = messageService;
        this.sessionStore = sessionStore;
    }


    @OnOpen
    public void onOpen(Session session,
                       @PathParam("username") String username,
                       @PathParam("message_type") String messageType,
                       @PathParam("is_deleted") boolean isDeleted) {
        logger.info("Entered into Open");
        // Set timeout for the session
        session.setMaxIdleTimeout(600000); // 10 minutes
        messageService.handleOpenSession(session, username, MessageEntity.MessageType.valueOf(messageType),isDeleted);
        messageService.broadcastJoinMessage(username);
    }


    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("Entered into Message: Got Message:" + message);
        messageService.handleIncomingMessage(session, message);
    }


    @OnClose
    public void onClose(Session session) {
        logger.info("Entered into Close");
        messageService.handleCloseSession(session);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {

        logger.error("Error encountered", throwable);
    }
}

