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

    /**
     * Retrieves all messages.
     *
     * @return ResponseEntity containing a list of MessageEntity objects if successful, or an internal server error.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<MessageEntity>> getAllMessages() {
        List<MessageEntity> messages = messageService.getAllMessages(MessageEntity.MessageType.BROADCAST, false);

        return ok(messages);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return ResponseEntity containing a MessageEntity object if found, or a not found response if not found, or an internal server error.
     */
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
    /**
     * Sends a new message.
     *
     * @param args MessageData object containing information for the new message.
     * @return ResponseEntity containing a success message if the message is saved successfully, or an internal server error.
     */
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
    /**
     * Deletes a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     * @return ResponseEntity with no content if the message is deleted successfully, or an internal server error.
     */
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
