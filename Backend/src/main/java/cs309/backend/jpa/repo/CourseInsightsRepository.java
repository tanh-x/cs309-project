package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.RateCourseInsightsEntity;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CourseInsightsRepository extends JpaRepository<RateCourseInsightsEntity, Integer> {
    // Add custom query methods if required
    @Procedure(name = "readCourseInsightsTable")
    TestEntity readCourseInsightsTable(@Param("p_id") int id);

//    @Procedure(name = "getCoByUid")
//    StudentEntity getStudentByUid(@Param("p_uid") int uid);
}
