package cs309.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/")
    public String root() { return "<body>Fallback root response</body>"; }
}
