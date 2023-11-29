package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.user.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
}
