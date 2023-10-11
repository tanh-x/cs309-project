package cs309.backend.controllers;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.models.*;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-test-data/{id}")
    public ResponseEntity<TestEntity> getTestDataEndpoint(@PathVariable int id) {
        try {
            return ok(userService.readTestTable(id));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerEndpoint(@RequestBody RegistrationData args) {
        try {
            userService.registerUser(args);
            return ok("{\"message\": \"Successfully registered new user\"}");
        } catch (RuntimeException e) {
            return internalServerError().body(e.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<SessionTokenData> loginEndpoint(@RequestBody LoginData args) {
        try {
            SessionTokenData jwt = userService.loginUser(args);
            return ok(jwt);
        } catch (InvalidCredentialsException e) {
            return status(UNAUTHORIZED).body(SessionTokenData.FAILED_LOGIN);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        } finally {
            System.out.println("Handled login request for " + args.email());
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable int id) {
        try {
            UserEntity user = userService.getUserByUid(id);
            return ok(UserData.fromEntity(user));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserData> getUserByEmail(@PathVariable String email) {
        try {
            UserEntity user = userService.getUserByEmail(email);
            if (user == null) return notFound().build();
            return ok(UserData.fromEntity(user));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {
        try {
            UserEntity user = userService.getUserByUsername(username);
            if (user == null) return notFound().build();
            return ok(UserData.fromEntity(user));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateInfoData updateInfo) {
        try {
            Boolean res = userService.updateUser(
                updateInfo.id(),
                updateInfo.email(),
                updateInfo.displayName()
            );
            return res ? ok("Success") : badRequest().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
