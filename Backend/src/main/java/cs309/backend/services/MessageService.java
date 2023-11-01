package cs309.backend.services;

import cs309.backend.Component.SessionStore;
import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.MessageRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.models.MessageData;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final SessionStore sessionStore;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(SessionStore sessionStore, MessageRepository messageRepository, UserRepository userRepository) {
        this.sessionStore = sessionStore;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // For WebSocket
    public void handleOpenSession(Session session, String username) {
        sessionStore.getSessionUsernameMap().put(session, username);
        sessionStore.getUsernameSessionMap().put(username, session);
        sendMessageToParticularUser(username, getChatHistory());
    }

    public void handleIncomingMessage(Session session, String message) {
        String username = sessionStore.getSessionUsernameMap().get(session);

        UserEntity user = userRepository.getUserByUsername(username);
        if (user == null || !Objects.equals(user.getUsername(), username))
            throw new IllegalArgumentException("Invalid user");

        if (message.startsWith("@")) {
            String receiverUsername = message.split(" ")[0].substring(1);
            UserEntity receiverUser = userRepository.getUserByUsername(receiverUsername);

            sendMessageToParticularUser(receiverUsername, "[DM] " + username + ": " + message);
            sendMessageToParticularUser(username, "[DM] " + username + ": " + message);

            saveMessage(new MessageData(user.getUid(), receiverUser.getUid(), message));
        } else {
            broadcast(username + ": " + message);
            saveMessage(new MessageData(user.getUid(), null, message));
        }

    }

    public void handleCloseSession(Session session) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        sessionStore.getSessionUsernameMap().remove(session);
        sessionStore.getUsernameSessionMap().remove(username);
        broadcast(username + " disconnected");
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            sessionStore.getUsernameSessionMap().get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Exception: " + e.getMessage());
        }
    }


    public void sendMessageToUser(String username, String message) {

        sendMessageToParticularUser(username, message);
    }
    // For CRUD

    public void saveMessage(MessageData args) {
        messageRepository.saveMessage(args.sender(), args.receiver(), args.content());
    }

    public List<MessageEntity> getAllMessages() {
        return new ArrayList<>(messageRepository.getAllMessages());
    }

    public MessageEntity getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public void deleteMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }


    private String getChatHistory() {
        List<MessageEntity> recentMessages = messageRepository.getAllMessages();
        StringBuilder history = new StringBuilder();
        for (MessageEntity message : recentMessages) {
            int senderUid = message.getSender();
            String senderName = userRepository.getUserByUid(senderUid).toString();
            history.append(senderName).append(": ").append(message.getContent()).append("\n");
        }
        return history.toString();
    }

    private void broadcast(String message) {
        sessionStore.getSessionUsernameMap().forEach((session, user) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("Exception: " + e.getMessage());
            }
        });
    }
}