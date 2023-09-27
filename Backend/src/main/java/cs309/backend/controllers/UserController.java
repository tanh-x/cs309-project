package cs309.backend.controllers;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.models.RegistrationData;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

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
    public ResponseEntity<String> registerEndpoint(@RequestBody RegistrationData args) {
        try {
            userService.regsiterUser(args);
            return ok("Successfully registered new user");
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }
}
