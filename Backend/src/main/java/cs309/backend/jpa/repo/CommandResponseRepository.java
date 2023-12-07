
package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.user.CommandResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandResponseRepository extends JpaRepository<CommandResponseEntity, Long> {
    Optional<CommandResponseEntity> findByCommand(String command);
    List<CommandResponseEntity> findAll();
}

