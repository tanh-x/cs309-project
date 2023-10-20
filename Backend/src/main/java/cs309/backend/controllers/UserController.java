package cs309.backend.controllers;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.User;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import cs309.backend.models.UserData;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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

    @GetMapping("id/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable int id) {
        try {
            UserEntity user = userService.getUserById(id);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserData> getUserByEmail(@PathVariable String email) {
        try {
            UserEntity user = userService.getUserByEmail(email);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {
        try {
            UserEntity user = userService.getUserByUsername(username);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }
    @PutMapping("{id}/{email}/{display_name}")
    public ResponseEntity<Boolean> updateUser(@PathVariable int id, @PathVariable String email, @PathVariable String display_name) {
        try {
            Boolean res = userService.updateUser(id, email, display_name);
            return ok(res);
        }
        catch (Exception e) {
            return internalServerError().build();
        }
    }
}
