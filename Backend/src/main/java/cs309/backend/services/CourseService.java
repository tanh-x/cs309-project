package cs309.backend.services;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.entity.ScheduleEntity;
import cs309.backend.jpa.entity.SectionEntity;
import cs309.backend.jpa.entity.user.CourseInsightsEntity;
import cs309.backend.jpa.repo.CourseInsightsRepository;
import cs309.backend.jpa.repo.CourseRepository;
import cs309.backend.jpa.repo.SectionRepository;
import cs309.backend.DTOs.SectionData;
import cs309.backend.util.scheduling.CourseHelper;
import cs309.backend.util.scheduling.SchedulingTable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final CourseInsightsRepository insightsRepository;

    @Autowired
    public CourseService(
        CourseRepository courseRepository,
        SectionRepository sectionRepository,
        CourseInsightsRepository insightsRepository
    ) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.insightsRepository = insightsRepository;
    }

    public List<CourseEntity> getAllCourseInformation(int term) {
        //return courseRepository.getAllCourseInformation(term);
        return courseRepository.findAll();
//    public ArrayList<ArrayList<ArrayList<CourseHelper>>> getCourseList(ArrayList<String> inputList) {
//        for(String i: inputList)
//        {
//            ArrayList<ScheduleEntity> lectures = new ArrayList<>();
//            ArrayList<ScheduleEntity> recitations =  new ArrayList<>();
//
//            Pattern pattern = Pattern.compile("^(.*?)(\\d+)$");
//            Matcher matcher = pattern.matcher(i);
//
//            if (matcher.matches()) {
//                // Extract the matched groups
//                String prefix = matcher.group(1).trim();
//                int num = Integer.parseInt(matcher.group(2));
//
//                CourseEntity course = getCourseByIdentifier(prefix, num);
//
//                SectionEntity[] section = getSectionById(course.getId());
//
//                for(SectionEntity s: section){
//                    if (isInteger(s.getSection())){
//                        lectures = new ArrayList<>(Arrays.asList(getScheduleById(s.getId())));
//                        }
//                    else{
//                        recitations =  new ArrayList<>(Arrays.asList(getScheduleById(s.getId())));
//                    }
//                }
//            }
//        }
//    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public CourseEntity[] getAllCourseInformation(int term) {
        return courseRepository.getAllCourseInformation(term);
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
}
