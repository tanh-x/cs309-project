package cs309.backend.jpa.repo;

import cs309.backend.jpa.entity.user.CourseInsightsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CourseInsightsRepository extends JpaRepository<CourseInsightsEntity, Long> {
    @Procedure(name = "getCourseInsights")
    CourseInsightsEntity[] getCourseInsights(@Param("p_course_id") int courseId);
}
