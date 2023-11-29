package cs309.backend.controllers;

import cs309.backend.util.scheduling.SchedulingTable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {


    @GetMapping("guh")
    public ResponseEntity<Object> guh()
    {
        SchedulingTable.doShit();

        return ok().build();
    }
}
