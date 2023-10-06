package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.user.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Procedure(name = "getStudentByUid")
    StudentEntity getStudentByUid(@Param("p_uid") int uid);
}
