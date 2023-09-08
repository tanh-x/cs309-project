package com.kewargs.cs309.backend.controllers;

import com.kewargs.cs309.backend.models.TestDataModel;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class TestController {
    @GetMapping("/")
    public String root() { return "<body>Does this work?</body>"; }


    @GetMapping("/api/echo/{bar}")
    public String echo(@PathVariable String bar) { return "Echo: " + bar; }


    @PostMapping("/api/testPost")
    public String testPost(@RequestBody TestDataModel args) {
        if (args == null) throw new IllegalArgumentException("Message was not given");

        return "Received: " + args.getMessage() +
                "; optional: " + Arrays.toString(args.getOptional());
    }
}


