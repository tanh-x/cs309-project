package cs309.backend.controllers;

import cs309.backend.jpa.entity.RateCourseInsightsEntity;
import cs309.backend.services.CourseService;
import cs309.backend.services.RateCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateCourseController {

    private final RateCourseService ratecourseService;

    public RateCourseController(RateCourseService ratecourseService) {
        this.ratecourseService = ratecourseService;
    }

    @GetMapping("api/ratecourse/{courseId}")
    public ResponseEntity<RateCourseInsightsEntity> getCourseByID(@PathVariable int courseId) {
        try {
            RateCourseInsightsEntity course = ratecourseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
