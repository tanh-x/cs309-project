package cs309.backend.controllers;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cs309.backend.Component.SessionStore;

import cs309.backend.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/{username}")
public class ChatSocketController {
	private final Logger logger = LoggerFactory.getLogger(ChatSocketController.class);

	@Autowired
	private final SessionStore sessionStore;
	private final MessageService messageService;

	@Autowired
	public ChatSocketController(MessageService messageService, SessionStore sessionStore) {
		this.messageService = messageService;
		this.sessionStore = sessionStore;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("Entered into Open");
		messageService.handleOpenSession(session, username);
		broadcast("User:" + username + " has joined the Chat");
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

	private void broadcast(String message) {
		sessionStore.getSessionUsernameMap().forEach((session, username) -> messageService.sendMessageToUser(username, message));
	}
}

