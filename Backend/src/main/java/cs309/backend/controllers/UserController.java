package cs309.backend.controllers;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.models.LoginData;
//import cs309.backend.jpa.entity.TestEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import cs309.backend.models.Response.BaseRS;
import cs309.backend.models.Users;
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
    BaseRS<Users> response;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        response = new BaseRS<Users>(true);
    }

    @GetMapping("/get-test-data/{id}")
    public TestEntity getTestDataEndpoint(@PathVariable int id) {
        System.out.println("Getting test data for id " + id);
        return userService.readTestTable(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerEndpoint(@RequestBody RegistrationData args) {
        try {
            userService.registerUser(args);
            return ok("Successfully registered new user");
        } catch (RuntimeException ex) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<SessionTokenData> loginEndpoint(@RequestBody LoginData args) {
        try {
            SessionTokenData jwt = userService.loginUser(args);
            return ok(jwt);
        } catch (InvalidCredentialsException ex) {
            return status(HttpStatus.UNAUTHORIZED).body(SessionTokenData.FAILED_LOGIN);
        } finally {
            System.out.println("Handled login request for " + args.email());
        }
    }
    /*@PostMapping("/register")
    public String registerEndpoint(@RequestBody RegistrationData args) {
        return "";*/


    @GetMapping("/{id}")
    public BaseRS<Users> getUserById(@PathVariable int id) {
        try {
            Users user = userService.getUser(1, id);
            response.setOutput(user);
        } catch (Exception e) {        //DEAL WITH ERROR LATER
            response.setSuccess(false);
            response.setExceptionCode(e.toString());
            return response;
        }
        return response;
    }

    @GetMapping("/{uid}")
    public BaseRS<Users> getUserByUID(@PathVariable int uid) {
        try {
            Users user = userService.getUser(2, uid);
            response.setOutput(user);
        } catch (Exception e) {        //DEAL WITH ERROR LATER
            response.setSuccess(false);
            response.setExceptionCode(e.toString());
            return response;
        }
        return response;
    }

    @GetMapping("/{email}")
    public BaseRS<Users> getUserByEmail(@PathVariable String email) {
        try {
            Users user = userService.getUser(1, email);
            response.setOutput(user);
        } catch (Exception e) {        //DEAL WITH ERROR LATER
            response.setSuccess(false);
            response.setExceptionCode(e.toString());
            return response;
        }
        return response;
    }

    @GetMapping("/{username}")
    public BaseRS<Users> getUserByUsername(@PathVariable String username) {
        try {
            Users user = userService.getUser(1, username);
            response.setOutput(user);
        } catch (Exception e) {        //DEAL WITH ERROR LATER
            response.setSuccess(false);
            response.setExceptionCode(e.toString());
            return response;
        }
        return response;
    }

    /*@PostMapping("/")
    public BaseRS<Users> createNewUser(@RequestBody Users user) {
        try {

        }
    }*/
}
