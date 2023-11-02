package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    @Procedure(name = "getSectionById")
    SectionEntity[] getSectionById(@Param("p_id") int id);
}
