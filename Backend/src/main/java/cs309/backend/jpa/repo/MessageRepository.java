package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {


    @Procedure(name = "saveMessage")
    void saveMessage(@Param("p_sender") int sender, @Param("p_receiver") Integer receiver, @Param("content") String content);

    List<MessageEntity> findByMessageTypeAndIsDeleted(MessageEntity.MessageType messageType, boolean isDeleted);




}
