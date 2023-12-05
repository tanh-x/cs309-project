package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.user.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
}
