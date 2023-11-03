package cs309.backend.services;
import cs309.backend.jpa.entity.RateCourseInsightsEntity;
import cs309.backend.jpa.repo.CourseInsightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RateCourseService {


    private final CourseInsightsRepository courseInsightsRepository;

    @Autowired
    public RateCourseService(CourseInsightsRepository courseInsightsRepository) {
        this.courseInsightsRepository = courseInsightsRepository;
    }

    public RateCourseInsightsEntity getCourseById(int courseId) {
        Optional<RateCourseInsightsEntity> courseOptional = courseInsightsRepository.findById(courseId);

        if(courseOptional.isPresent()) {
            return courseOptional.get();
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }
}

