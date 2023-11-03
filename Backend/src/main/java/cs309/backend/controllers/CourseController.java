package cs309.backend.controllers;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.entity.SectionEntity;
import cs309.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourseById(@PathVariable int id) {
        try {
            CourseEntity res = courseService.getCourseById(id);
            return ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @PutMapping("/{identifier}/{num}/{description}")
    public ResponseEntity<String> updateCourseDescription(@PathVariable String identifier, @PathVariable int num, @PathVariable String description) {
        try {
            String res = courseService.updateCourseInfo(identifier, num, description);
            return ok("Successful!");
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<SectionEntity[]> getSectionById(@PathVariable int id) {
        try {
            SectionEntity[] res = courseService.getSectionById(id);
            return ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
