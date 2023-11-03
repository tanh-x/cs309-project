package cs309.backend.services;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.entity.SectionEntity;
import cs309.backend.jpa.repo.CourseRepository;
import cs309.backend.jpa.repo.SectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    public CourseEntity[] getAllCourseInformation(int term) {
        return courseRepository.getAllCourseInformation(term);
    }

    public CourseEntity getCourseById(int id) {
        return courseRepository.getCourseById(id);
    }

    public SectionEntity[] getSectionById(int id) {
        return sectionRepository.getSectionById(id);
    }

    public String updateCourseByIdentifier(String identifier ,int num, String description) {
        CourseEntity course = getCourseByIdentifier(identifier, num);
        if (course == null) {
            return "Course Not Found";
        }
        courseRepository.updateCourseByIdentifier(identifier, num, description);
        return "Successful";
    }

    public CourseEntity getCourseByIdentifier(String identifier, int num) { return courseRepository.getCourseByIdentifier(identifier, num); }
}
