package cs309.backend.controllers;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.models.MessageData;
import cs309.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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

            return messageService.getAllMessages();

        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/message/{messageId}")
    public ResponseEntity<MessageData> getMessageById(@PathVariable int messageId) {
        try {
            MessageEntity messageEntity = messageService.getMessageById(messageId);
            if (messageEntity == null) {
                return ResponseEntity.notFound().build();
            }
            MessageData messageData = MessageData.fromEntity(messageEntity);

            return ResponseEntity.ok(messageData);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageData args) {
        try {
            messageService.saveMessage(args);
            return ResponseEntity.ok("Message saved successfully");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
        try {

            messageService.deleteMessage(messageId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
