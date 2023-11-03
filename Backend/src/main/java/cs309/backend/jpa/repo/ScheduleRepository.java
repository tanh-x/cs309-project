package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

}
