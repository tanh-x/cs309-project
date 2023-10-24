package cs309.backend.controllers;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.models.LoginData;
import cs309.backend.models.RegistrationData;
import cs309.backend.models.SessionTokenData;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerEndpoint(@RequestBody RegistrationData args) {
        try {
            userService.registerUser(args);
            return ok("Successfully registered new user");
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
        } finally {
            System.out.println("Handled login request for " + args.email());
        }
    }
}
