package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Integer> {

}
