package cs309.backend.services;
import cs309.backend.jpa.entity.chat.ChatMessageEntity;
import cs309.backend.jpa.repo.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ChatService {

    @Autowired
    ChatMessageRepository ChatmessageRepository;

    public void saveMessage(ChatMessageEntity message) {
        ChatMessageRepository.save(message);
    }

    public List<ChatMessageEntity> getChatHistory(String userId) {
        // Logic to get chat history based on user
        return new ArrayList<>();
    }
}

