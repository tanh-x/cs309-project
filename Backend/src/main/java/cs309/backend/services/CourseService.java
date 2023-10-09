package cs309.backend.services;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.repo.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository repo) {
        this.courseRepository = repo;
    }

    public CourseEntity[] getAllCourseInformation(int term) {
        return courseRepository.getAllCourseInformation(term);
    }
}
