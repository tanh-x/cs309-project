package cs309.backend.controllers;

import cs309.backend.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reader")
public class DegreeAuditController {
    private final ReaderService readerService;

    @PostMapping("/pdf")
    public String readingDegreeAudit(@RequestBody MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return readerService.extractTextFromPdf(inputStream);
        }
    }
}
