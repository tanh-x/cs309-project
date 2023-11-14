package cs309.backend.controllers;

import cs309.backend.exception.InvalidCredentialsException;
import cs309.backend.DTOs.LoginData;
import cs309.backend.DTOs.RegistrationData;
import cs309.backend.DTOs.SessionTokenData;
import cs309.backend.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "register a new Account, generating a new Jwt token for that user")
    @PostMapping("/register")
    public ResponseEntity<String> registerEndpoint(@RequestBody RegistrationData args) {
        try {
            userService.registerUser(args);
            return ok("Successfully registered new user");
        } catch (RuntimeException e) {
            return internalServerError().body(e.toString());
        }
    }

    @Operation(description = "login to the account, check the credentials")
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
