package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.StudentProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProgramRepository extends JpaRepository<StudentProgramEntity, Integer> {
    int countByUid(int uid);

    StudentProgramEntity findByUid(int uid);
}
