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
        sendMessageToParticularUser(username, getChatHistory(username));
    }

    public void handleIncomingMessage(Session session, String message) {
            String username = sessionStore.getSessionUsernameMap().get(session);

            try {
                UserEntity user = userRepository.getUserByUsername(username);

                // Assuming that you have already handled user validation elsewhere

                if (message.startsWith("@")) {
                    String receiverUsername = message.split(" ")[0].substring(1);

                    if (receiverUsername.equals(username)) {
                        sendMessageToParticularUser(username, "You cannot send a private message to yourself.");
                        return;
                    }

                    UserEntity receiverUser = userRepository.getUserByUsername(receiverUsername);
                    if (receiverUser == null) {
                        sendMessageToParticularUser(username, "The user '" + receiverUsername + "' does not exist.");
                        return;
                    }

                    String contentToSave = message.substring(receiverUsername.length() + 2); // +2 for '@' and space
                    String formattedMessage = "[DM] " + username + ": " + contentToSave;

                    sendMessageToParticularUser(receiverUsername, formattedMessage);
                    sendMessageToParticularUser(username, formattedMessage); // Confirm to sender that the message was sent

                    saveMessage(new MessageData(user.getUid(), receiverUser.getUid(), contentToSave));
                } else {
                    // Handle non-direct messages
                    broadcastExcludeSender(username + ": " + message, username);
                    saveMessage(new MessageData(user.getUid(), null, message));
                }
            } catch (Exception e) {
                logger.error("An error occurred while processing the message: " + e.getMessage(), e);
                sendMessageToParticularUser(username, "An error occurred. Please try again later.");
            }
        }


        public void handleCloseSession(Session session) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        sessionStore.getSessionUsernameMap().remove(session);
        sessionStore.getUsernameSessionMap().remove(username);
        broadcastExcludeSender(username + " disconnected",username);
    }

    private void sendMessageToParticularUser(String username, String message) {
        Session session = sessionStore.getUsernameSessionMap().get(username);

        if (session != null && session.isOpen()) {

            try {
            sessionStore.getUsernameSessionMap().get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Exception: " + e.getMessage());
        }
        } else {
            logger.error("Attempted to send message to non-existent or closed session for username: " + username);
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


    private String getChatHistory(String username) {
    //private String getChatHistory() {

        List<MessageEntity> recentMessages = messageRepository.getAllMessages();
        StringBuilder history = new StringBuilder();
        for (MessageEntity message : recentMessages) {
            int senderUid = message.getSender();
//            String senderName = userRepository.getUserByUid(senderUid).   toString();
//            history.append(senderName).append(": ").append(message.getContent()).append("\n");
            UserEntity sender = userRepository.getUserByUid(senderUid);
            if (!sender.getUsername().equals(username)) {
                history.append(sender.getUsername()).append(": ").append(message.getContent()).append("\n");
            }
        }
        return history.toString();
    }

    private void broadcastExcludeSender(String message,String senderUsername) {
        sessionStore.getSessionUsernameMap().forEach((session, username) -> {
            if (!username.equals(senderUsername)) {

                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    logger.error("Exception: " + e.getMessage());
                }
            }
        });
    }
}
