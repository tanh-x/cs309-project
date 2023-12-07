package cs309.backend.services;

import cs309.backend.Component.SessionStore;
import cs309.backend.DTOs.MessageData;
import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.user.CommandResponseEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.jpa.repo.CommandResponseRepository;
import cs309.backend.jpa.repo.MessageRepository;
import cs309.backend.jpa.repo.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class MessageService {
    private static final int CHAT_HISTORY_LENGTH_CUTOFF = 100;

    private final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final SessionStore sessionStore;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CommandResponseRepository commandResponseRepository;


    @Autowired
    public MessageService(SessionStore sessionStore, MessageRepository messageRepository, UserRepository userRepository, CommandResponseRepository commandResponseRepository) {
        this.sessionStore = sessionStore;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.commandResponseRepository = commandResponseRepository;

    }

    // For WebSocket
    public void handleOpenSession(Session session, String username,MessageEntity.MessageType messageType, boolean isDeleted) {
        sessionStore.getSessionUsernameMap().put(session, username);
        sessionStore.getUsernameSessionMap().put(username, session);
        sendMessageToUser(username, getChatHistory(messageType,isDeleted));
    }

    public void handleIncomingMessage(Session session, String message) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        String response = processCommand(message);
        if (response != null) {
            sendMessageToSession(session, response);
        }
        UserEntity user = userRepository.getUserByUsername(username);
        if (user == null || !Objects.equals(user.getUsername(), username)){
            logger.error("Invalid user: " + username);
        sendMessageToSession(session, "Error: Invalid user.");
        return;
    }
        if (message.startsWith("@")) {
            String receiverUsername = message.split(" ")[0].substring(1);
            UserEntity receiverUser = userRepository.getUserByUsername(receiverUsername);

            String messageContent = "[DM] " + user.getDisplayName() + ": " + message;
            sendMessageToUser(receiverUsername, messageContent);
            sendMessageToUser(username, messageContent);

            saveMessage(new MessageData(user.getUid(), receiverUser.getUid(), message));
        } else {
            broadcast(user.getDisplayName() + ": " + message);
            saveMessage(new MessageData(user.getUid(), null, message));
        }



    }

    public void handleCloseSession(Session session) {
        String username = sessionStore.getSessionUsernameMap().get(session);
        sessionStore.getSessionUsernameMap().remove(session);
        sessionStore.getUsernameSessionMap().remove(username);
        broadcast(username + " disconnected");
    }

public void sendMessageToSession(Session session, String message) {
    if (session == null) {
        logger.error("Session is null. Cannot send message.");
        return;
    }

    try {
        session.getBasicRemote().sendText(message);
    } catch (IOException e) {
        logger.error("Exception in sending message to session: " + e.getMessage());
    }
}
    public void sendMessageToUser(String username, String message) {
        Session userSession = sessionStore.getUsernameSessionMap().get(username);
        if (userSession == null) {
            logger.error("No session available for user: " + username);
            return;
        }

        try {
            userSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("Exception in sending message: " + e.getMessage());
        }
    }


    public void saveMessage(MessageData args) {
        messageRepository.saveMessage(args.sender(), args.receiver(), args.content());
    }

    public List<MessageEntity> getAllMessages(MessageEntity.MessageType messageType, boolean isDeleted) {
        return new ArrayList<>(messageRepository.findByMessageTypeAndIsDeleted(messageType,isDeleted));
    }

    public MessageEntity getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public void deleteMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }


    private String getChatHistory(MessageEntity.MessageType messageType, boolean isDeleted) {
        List<MessageEntity> recentMessages = messageRepository.findByMessageTypeAndIsDeleted(messageType,isDeleted);
        int n = recentMessages.size();
        recentMessages.subList(Math.max(0, n - CHAT_HISTORY_LENGTH_CUTOFF), n);

        StringBuilder history = new StringBuilder();

        Map<Integer, String> authors = new HashMap<>();

        for (MessageEntity message : recentMessages) {
            int senderUid = message.getSender();
            String senderName;

            if (authors.containsKey(senderUid)) {
                senderName = authors.get(senderUid);
            } else {
                senderName = userRepository.getReferenceById(senderUid).getDisplayName();
                authors.put(senderUid, senderName);
            }

            history.append(senderName).append(": ").append(message.getContent()).append("\n");
        }
        return history.toString();
    }

    public void broadcastJoinMessage(String username) {
        broadcast(userRepository.getUserByUsername(username).getDisplayName() + " has joined the chat.");
    }

    public void broadcast(String message) {
        sessionStore.getSessionUsernameMap().forEach((session, user) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("Exception: " + e.getMessage());
            }
        });
    }

private String processCommand(String message) {
    if (message.startsWith("!")) {
        String command = message.substring(1).split("\\s+")[0];
        return commandResponseRepository.findByCommand(command)
                .map(CommandResponseEntity::getResponse)
                .orElse(null);
    }
    return null;
}
    public List<CommandResponseEntity> getAllCommandResponses() {
        return commandResponseRepository.findAll();
    }

    /// EVENT
//    public void sendCalendarEvents(Session session) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime endOfWeek = now.plusWeeks(1);
//        List<EventEntity> events = EventRepository.findByStartDateBetween(now, endOfWeek);
//        try {
//            String eventsData = ObjectMapper.writeValueAsString(events);
//            sendMessageToSession(session, eventsData);
//        } catch (JsonProcessingException e) {
//            logger.error("Error converting events to JSON", e);
//        }
//    }
//


}
