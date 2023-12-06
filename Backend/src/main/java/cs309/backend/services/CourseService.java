package cs309.backend.services;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.entity.ScheduleEntity;
import cs309.backend.jpa.entity.SectionEntity;
import cs309.backend.jpa.entity.user.CourseInsightsEntity;
import cs309.backend.jpa.repo.CourseInsightsRepository;
import cs309.backend.jpa.repo.CourseRepository;
import cs309.backend.jpa.repo.ScheduleRepository;
import cs309.backend.jpa.repo.SectionRepository;
import cs309.backend.DTOs.SectionData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final CourseInsightsRepository insightsRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public CourseService(
        CourseRepository courseRepository,
        SectionRepository sectionRepository,
        CourseInsightsRepository insightsRepository,
        ScheduleRepository scheduleRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.insightsRepository = insightsRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<CourseEntity> getAllCourseInformation(int term) {
        //return courseRepository.getAllCourseInformation(term);
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(int id) {
        return courseRepository.getCourseById(id);
    }

    public SectionEntity[] getSectionById(int courseId) {
        return sectionRepository.getAllSectionByCourseId(courseId);
    }

    public String updateCourseByIdentifier(String identifier, int num, String description) {
        CourseEntity course = getCourseByIdentifier(identifier, num);
        if (course == null) {
            return "Course Not Found";
        }
        course.setDescription(description);
        courseRepository.save(course);
        //courseRepository.updateCourseByIdentifier(identifier, num, description);
        return "Successful";
    }

    public CourseEntity getCourseByIdentifier(String identifier, int num) { return courseRepository.getCourseByProgramIdentifierAndNum(identifier, num); }

    public String createSection(SectionData args) {
        CourseEntity course = getCourseByIdentifier(args.identifier(), args.num());
        if (course == null) {
            return "Course Not Found";
        }
        SectionEntity section =  new SectionEntity(args.ref(),
                args.section(),
                args.year(),
                args.season(),
                args.is_online(),
                course);
        /*sectionRepository.createSection(
            args.ref(),
            args.identifier(),
            args.num(),
            args.section(),
            args.year(),
            args.season(),
            args.is_online()
        );*/
        sectionRepository.save(section);
        return "Successful!";
    }

    public CourseInsightsEntity[] getCourseInsights(int courseId) {
        return insightsRepository.getCourseInsights(courseId);
    }

    public ScheduleEntity[] getScheduleBySectionId(int id) {
        return scheduleRepository.findAllBySectionId(id);
    }
}
