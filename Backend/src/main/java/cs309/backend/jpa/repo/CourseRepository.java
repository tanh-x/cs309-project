package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    /*@Procedure(name = "getAllCourseInformation")
    CourseEntity[] getAllCourseInformation(@Param("p_term") int term);*/
    //@Procedure(name = "getCourseById")
    CourseEntity getCourseById(int id);

    /*@Procedure(name = "getCourseByIdentifier")
    CourseEntity getCourseByIdentifier(@Param("p_identifier") String identifier, @Param("p_num") int num);*/

    CourseEntity getCourseByProgramIdentifierAndNum(String identifier, int num);

    @Procedure(name = "updateCourseByIdentifier")
    void updateCourseByIdentifier(@Param("p_identifier") String identifier,@Param("p_num") int num,@Param("p_des") String description);
}
