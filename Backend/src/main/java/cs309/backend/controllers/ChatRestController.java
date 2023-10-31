package cs309.backend.controllers;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.models.MessageData;
import cs309.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final MessageService messageService;

    @Autowired
    public ChatRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageData>> getAllMessages() {
        try {
            return ResponseEntity.ok(messageService.getAllMessages());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/message/{messageId}")
    public ResponseEntity<MessageData> getMessageById(@PathVariable int messageId) {
        try {
            MessageData message = messageService.getMessageById(messageId);
            if (message == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<MessageData> sendMessage(@RequestBody MessageData messageData) {
        try {
            return ResponseEntity.ok(messageService.saveMessage(messageData));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        try {
            messageService.deleteMessage(messageId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
