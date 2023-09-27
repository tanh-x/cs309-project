package cs309.backend.controllers;

//import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.Response.BaseRS;
import cs309.backend.models.Users;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    /*@GetMapping("/get-test-data/{id}")
    public TestEntity getTestDataEndpoint(@PathVariable int id) {
        System.out.println("Getting test data for id " + id);
        return userService.readTestTable(id);
    }

    /*@PostMapping("/register")
    public String registerEndpoint(@RequestBody RegistrationData args) {
        return "";*/


    @GetMapping("/{id}")
    public BaseRS<Users> getUserById(@PathVariable int id) {
        try {
            Users user = userService.getUser(1, id);
            response.setOutput(user);
        }
        catch (Exception e){        //DEAL WITH ERROR LATER
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
        }
        catch (Exception e){        //DEAL WITH ERROR LATER
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
        }
        catch (Exception e){        //DEAL WITH ERROR LATER
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
        }
        catch (Exception e){        //DEAL WITH ERROR LATER
        response.setSuccess(false);
        response.setExceptionCode(e.toString());
        return response;
        }
        return response;
        }
    }
}
