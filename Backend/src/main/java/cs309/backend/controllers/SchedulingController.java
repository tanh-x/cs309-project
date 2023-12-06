package cs309.backend.controllers;

import cs309.backend.services.CourseService;
import cs309.backend.util.scheduling.CourseHelper;
import cs309.backend.util.scheduling.SchedulingTable;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {
    @Autowired
    private final CourseService courseService;

    public SchedulingController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(description = "create schedule")
    @PostMapping("/schedinput")
    public ResponseEntity<ArrayList<ArrayList<ArrayList<CourseHelper>>>> getCourseList(@RequestBody ArrayList<String> inputList) {
        try {
            //           ArrayList<ArrayList<ArrayList<CourseHelper>>> st = courseService.getCourseList(inputList);
            // Process the List<String> content
            //chedulingTable st = new SchedulingTable(null); //process input
            // Return the schedule
            //return ok(st.getSchedule());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return null;
    }


    @GetMapping("guh")
    public ResponseEntity<Object> guh()
    {
        SchedulingTable.doShit();


        return ok().build();
    }
}
