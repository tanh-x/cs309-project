package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public abstract class TestEntityRepository implements JpaRepository<TestEntity, Long> {
    @Query(value = "CALL Scheduler.readTestTable(:id)", nativeQuery = true)
    public abstract TestEntity readTestData(@Param("id") int id);
}
