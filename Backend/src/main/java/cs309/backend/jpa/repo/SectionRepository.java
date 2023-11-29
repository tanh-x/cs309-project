package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    //@Procedure(name = "getSectionById")
    SectionEntity[] getAllSectionByCourseId(int id);

    @Procedure(name = "addSection")
    void createSection(@Param("p_ref") int ref,
                       @Param("p_program_identifier") String identifier,
                       @Param("p_num") int num,
                       @Param("p_section") String section,
                       @Param("p_year") int year,
                       @Param("p_season") int season,
                       @Param("p_is_online") boolean online);
}
