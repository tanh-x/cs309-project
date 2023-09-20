package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
    @Procedure(name = "readTestTable")
    TestEntity readTestTable(@Param("p_id") int id);
}
