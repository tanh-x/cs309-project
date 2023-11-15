package cs309.backend.controllers;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.DTOs.MessageData;
import cs309.backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

//import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final MessageService messageService;

    @Autowired
    public ChatRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getAllMessages() {
        return ok(messageService.getAllMessages());
    }

    @GetMapping("/message/{messageId}")
    public ResponseEntity<MessageEntity> getMessageById(@PathVariable int messageId) {
        try {
            MessageEntity messageEntity = messageService.getMessageById(messageId);
            if (messageEntity == null) return ResponseEntity.notFound().build();
            return ok(messageEntity);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageData args) {
        try {
            messageService.saveMessage(args);
            return ok("Message saved successfully");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
        try {

            messageService.deleteMessage(messageId);
            return ok().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
