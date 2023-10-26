package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.chat.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long>{

}
