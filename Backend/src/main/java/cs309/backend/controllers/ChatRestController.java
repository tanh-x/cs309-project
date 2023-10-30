package cs309.backend.controllers;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.models.MessageData;
import cs309.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final MessageService messageService;

    @Autowired
    public ChatRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<MessageData> getAllMessages() {
        return messageService.getAllMessages().stream()
                .map(MessageData::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/message/{messageId}")
    public ResponseEntity<MessageData> getMessageById(@PathVariable Long messageId) {
        return messageService.getMessageById(messageId)
                .map(message -> ResponseEntity.ok(MessageData.fromEntity(message)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/send")
    public ResponseEntity<MessageData> sendMessage(@RequestBody MessageData messageData) {
        if (messageData.sender() == null || messageData.content().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        MessageEntity savedMessage = messageService.saveMessage(messageData.toEntity());
        return ResponseEntity.ok(MessageData.fromEntity(savedMessage));
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        if (!messageService.existsById(messageId)) {
            return ResponseEntity.notFound().build();
        }
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }
}
