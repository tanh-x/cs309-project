package cs309.backend.controllers;

import cs309.backend.util.scheduling.SchedulingTable;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

//    @Operation(description = "create schedule")
//    @PostMapping("/pdf")
//    public ResponseEntity<LinkedHashSet<String>> readDegreeAudit(@RequestParam("file") MultipartFile file){
//        try (InputStream inputStream = file.getInputStream()) {
//            return ok(readerService.extractTextFromPdf(inputStream));
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return internalServerError().build();
//        }
//    }
    @GetMapping("guh")
    public ResponseEntity<Object> guh()
    {
        SchedulingTable.doShit();


        return ok().build();
    }
}
