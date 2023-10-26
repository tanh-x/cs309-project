package cs309.backend.controllers;

import cs309.backend.jpa.entity.chat.ChatMessageEntity;
import cs309.backend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {this.chatService = chatService; }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessageEntity message) {
        try {
            chatService.saveMessage(message);
            return ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
