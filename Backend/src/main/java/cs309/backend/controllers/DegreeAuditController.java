package cs309.backend.controllers;

import cs309.backend.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reader")
public class DegreeAuditController {
    private final ReaderService readerService;

    @PostMapping("/pdf")
    public LinkedHashSet<String> readingDegreeAudit(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return readerService.extractTextFromPdf(inputStream);
        }
    }
}
