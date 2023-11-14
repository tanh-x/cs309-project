package cs309.backend.controllers;

import cs309.backend.jpa.entity.CourseEntity;
import cs309.backend.jpa.entity.SectionEntity;
import cs309.backend.DTOs.SectionData;
import cs309.backend.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(description = "get all the courses in the given term")
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

    @Operation(description = "get course by course id")
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
    @Operation(description = "get course by its identifier and number")
    @GetMapping("/{identifier}/{num}")
    public ResponseEntity<CourseEntity> getCourseByIdentifier(@PathVariable @Schema(example = "COM S") String identifier, @PathVariable @Schema(example = "127") int num) {
        try {
            CourseEntity res = courseService.getCourseByIdentifier(identifier, num);
            return ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @Operation(description = "change the course descryption using its identifier and number")
    @PutMapping("/{identifier}/{num}")
    public ResponseEntity<String> updateCourseDescriptionByIdentifier(@PathVariable @Schema(example = "COM S") String identifier, @PathVariable @Schema(example = "127 ")int num, @RequestParam("description") @Schema(example = "Python")String description) {
        try {
            String res = courseService.updateCourseByIdentifier(identifier, num, description);
            return ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @Operation(description = "get the section by section id")
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

    @Operation(description = "create a new section")
    @PostMapping("/section")
    public ResponseEntity<String> createSection(@RequestBody SectionData args) {
        try {
            String res = courseService.createSection(args);
            return ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
