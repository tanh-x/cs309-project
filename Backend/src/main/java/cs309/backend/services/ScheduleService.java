package cs309.backend.services;

import cs309.backend.jpa.repo.CourseInsightsRepository;
import cs309.backend.jpa.repo.CourseRepository;
import cs309.backend.jpa.repo.SectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ScheduleService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    @Autowired
    public ScheduleService(
            CourseRepository courseRepository,
            SectionRepository sectionRepository
    ) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }


}
