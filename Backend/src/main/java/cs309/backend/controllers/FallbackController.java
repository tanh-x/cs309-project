package cs309.backend.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String getRoot() {
        return "Nothing here.";
    }
}
