package cs309.backend.controllers;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.models.RegistrationData;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-test-data/{id}")
    public TestEntity getTestDataEndpoint(@PathVariable int id) {
        System.out.println("Getting test data for id " + id);
        return userService.readTestTable(id);
    }

    @PostMapping("/register")
    public String registerEndpoint(@RequestBody RegistrationData args) {
        return "";
    }
}
