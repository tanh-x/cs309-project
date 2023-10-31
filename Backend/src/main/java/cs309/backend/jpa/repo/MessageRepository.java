package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Procedure(name = "saveMessage")
    void saveMessage(
            @Param("p_sender") int sender,
            @Param("p_receiver") int receiver,
            @Param("p_content") String content,
            @Param("p_sent_date") java.util.Date sentDate,
            @Param("p_message_type") String messageType
    );

    @Procedure(name = "getAllMessages")
    List<MessageEntity> getAllMessages();

    @Procedure(name = "getMessageById")
    MessageEntity getMessageById(@Param("p_message_id") int messageId);

    @Procedure(name = "deleteMessageById")
    void deleteMessageById(@Param("p_message_id") int messageId);

    @Procedure(name = "findBySenderUsername")
    List<MessageEntity> findBySenderUsername(@Param("p_username") String username);

    @Procedure(name = "getSenderIdByUsername")
    List<MessageEntity> getIdSenderByUsername(@Param("p_username") String username);
    @Procedure(name = "getReceiverIdByUser")
    List<MessageEntity> getReceiverIdByUser(@Param("p_username") String username);

    @Procedure(name = "readMessageTable")
    List<MessageEntity> readMessageTable(@Param("p_id") int id);


}
