package cs309.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.websocket.Session;

import cs309.backend.jpa.repo.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs309.backend.Component.SessionStore;
import cs309.backend.jpa.entity.MessageEntity;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final SessionStore sessionStore;

    // Constructor with SessionStore injection (you might also need other injections)
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(SessionStore sessionStore, MessageRepository messageRepository) {
        this.sessionStore = sessionStore;
        this.messageRepository = messageRepository;
    }
    public void sendMessageToUser(String username, String message) {
        sendMessageToParticularUser(username, message);
    }

    public void handleOpenSession(Session session, String username) throws IOException {
        sessionStore.getSessionUsernameMap().put(session, username);
        sessionStore.getUsernameSessionMap().put(username, session);
        sendMessageToParticularUser(username, getChatHistory());

    }


    public void handleIncomingMessage(Session session, String message) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        if (message.startsWith("@")) {
            String destUsername = message.split(" ")[0].substring(1);
            sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToParticularUser(username, "[DM] " + username + ": " + message);
        } else {
            broadcast(username + ": " + message);
        }
        // Assuming you have a repo for messages, save it here.
        // msgRepo.save(new MessageEntity(username, message));
    }

    public void handleCloseSession(Session session) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        sessionStore.getSessionUsernameMap().remove(session);
        sessionStore.getUsernameSessionMap().remove(username);
        String message = username + " disconnected";
        broadcast(message);
    }

    public void handleError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private void sendMessageToParticularUser(String username, String message) {
        try {
            sessionStore.getUsernameSessionMap().get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sessionStore.getSessionUsernameMap().forEach((session, user) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }


    public void saveMessageToRepo(Long sender, String content) {
        MessageEntity message = new MessageEntity();
        message.setSender(sender); // Save UID ínstead of username
        message.setContent(content);
        messageRepository.save(message);
    }

    public Optional<MessageEntity> getMessageByIdFromRepo(Long id) {
        return messageRepository.findById(id);  // Sử dụng phương thức tiêu chuẩn của JPA
    }


    public List<MessageEntity> getAllMessagesFromRepo() {
        return messageRepository.getAllMessages();
    }

    public void deleteMessageFromRepo(Long id) {
        messageRepository.deleteMessageById(id);
    }

    public List<MessageEntity> findMessagesBySenderUsernameFromRepo(String username) {
        return messageRepository.findBySenderUsername(username);
    }

    private String getChatHistory() {
        List<MessageEntity> recentMessages = getAllMessagesFromRepo();
        StringBuilder history = new StringBuilder();
        for (MessageEntity message : recentMessages) {
            history.append(message.getSender()).append(": ").append(message.getContent()).append("\n");
        }
        return history.toString();
    }
}
