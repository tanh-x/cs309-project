package cs309.backend.controllers;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all/{term}")
    public ResponseEntity<CourseEntity[]> getAll(@PathVariable int term) {
        try {
            CourseEntity[] courses = courseService.getAllCourseInformation(term);
            return ok(courses);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
