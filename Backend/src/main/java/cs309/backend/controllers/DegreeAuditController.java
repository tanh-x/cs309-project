package cs309.backend.controllers;

import cs309.backend.DTOs.DegreeAudit;
import cs309.backend.services.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reader")
public class DegreeAuditController {
    private final ReaderService readerService;

    /**
     * Reads a degree audit PDF file and retrieves all courses that have been finished or are currently being taken.
     *
     * @param file The PDF file containing the degree audit information.
     * @return ResponseEntity containing the parsed degrgee audit
     */
    @Operation(description = "get all the course that have been finished or taking in the degree audit")
    @PostMapping("/pdf")
    public ResponseEntity<DegreeAudit> readDegreeAudit(@RequestParam("file") MultipartFile file){
        try (InputStream inputStream = file.getInputStream()) {
            return ok(readerService.extractTextFromPdf(inputStream));
        }
        catch (IOException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
