package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    @Procedure(name = "getAllMessages")
    List<MessageEntity> getAllMessages();

    @Procedure(name = "saveMessage")
    void saveMessage(@Param("p_sender") int sender, @Param("p_receiver") Integer receiver, @Param("content") String content);


    // TODO: All the following procedures don't exist yet

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
