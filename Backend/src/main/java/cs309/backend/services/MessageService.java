package cs309.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.websocket.Session;

import cs309.backend.Component.SessionStore;
import cs309.backend.jpa.repo.MessageRepository;
import cs309.backend.jpa.repo.UserRepository;
import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.models.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public void handleOpenSession(Session session, String username) throws IOException {
        sessionStore.getSessionUsernameMap().put(session, username);
        sessionStore.getUsernameSessionMap().put(username, session);
        sendMessageToParticularUser(username, getChatHistory());
    }

    public void handleIncomingMessage(Session session, String message) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        int uid = userRepository.getIdByUsername(username);
        if(uid != -1) {
            if (message.startsWith("@")) {
                String destUsername = message.split(" ")[0].substring(1);
                sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + message);
                sendMessageToParticularUser(username, "[DM] " + username + ": " + message);
            } else {
                broadcast(username + ": " + message);
            }
            saveMessage(uid, message);
        }
    }

    public void handleCloseSession(Session session) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        sessionStore.getSessionUsernameMap().remove(session);
        sessionStore.getUsernameSessionMap().remove(username);
        broadcast(username + " disconnected");
    }

    public void handleError(Session session, Throwable throwable) {
        throwable.printStackTrace();
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
        int senderId = args.sender();
        int receiverId = args.receiver();
        String content = args.content();
        java.util.Date sentDate = args.sentDate();
        String messageTypeStr = args.messageType().toString();

        messageRepository.saveMessage(senderId, receiverId, content, sentDate, messageTypeStr);

    }
    public void saveMessage(int uid, String messageContent) {
        MessageEntity message = new MessageEntity();
        message.setSender(uid);
        message.setContent(messageContent);
        messageRepository.save(message);
    }

    public List<MessageData> getAllMessages() {

        return messageRepository.getAllMessages().stream()
                .map(MessageData::fromEntity)
                .collect(Collectors.toList());

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
