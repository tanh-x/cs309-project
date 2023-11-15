package cs309.backend.controllers;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.DTOs.ChangePasswordData;
import cs309.backend.DTOs.UpdateInfoData;
import cs309.backend.DTOs.UserData;
import cs309.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


  /*  @GetMapping("/get-test-data/{id}")
    public TestEntity getTestDataEndpoint(@PathVariable int id) {
        System.out.println("Getting test data for id " + id);
        return userService.readTestTable(id);
    }*/

    @Operation(description = "get user info by user id")
    @GetMapping("id/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable int id) {
        try {
            UserEntity user = userService.getUserByUid(id);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @Operation(description = "get user info by user email")
    @GetMapping("email/{email}")
    public ResponseEntity<UserData> getUserByEmail(@PathVariable @Schema(example = "duckhoi123456@gmail.com") String email) {
        try {
            UserEntity user = userService.getUserByEmail(email);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @Operation(description = "get user info by username")
    @GetMapping("username/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {
        try {
            UserEntity user = userService.getUserByUsername(username);
            return ok(UserData.fromEntity(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @Operation(description = "change the user email and display_name using id")
    @PutMapping("{id}/{email}/{display_name}")
    public ResponseEntity<Boolean> updateUser(@PathVariable int id, @PathVariable String email, @PathVariable String display_name) {
        try {
            Boolean res = userService.updateUser(id, email, display_name);
            return ok(res);
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @Operation(description = "update User info")
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateInfoData updateInfo) {
        try {
            Boolean res = userService.updateUser(
                updateInfo.id(),
                updateInfo.email(),
                updateInfo.displayName()
            );
            return ok(res);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }

    @Operation(description = "change the user password")
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordData req, Principal user) {
        try {
            String res = userService.changePassword(req, user);
            return ok(res);
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    /*@GetMapping("/principal")
    public Principal principal(Principal p) {
        return p;
    }*/

    @Operation(description = "delete the user")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(Principal user) {
        try {
            return ok(userService.deleteUser(user));
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    /*@DeleteMapping("/test/delete/{id}")
    public ResponseEntity<Boolean> testDeleteUser(@PathVariable int id) {
        try {
            return ok(userService.testDeleteUser(id));
        }
        catch (Exception e) {
            return internalServerError().build();
        }
    }*/
    @Operation(description = "grant permission for user to staff or admin")
    @PutMapping("/grant/{id}/{new_privilege}")
    public ResponseEntity<String> grantPermission(@PathVariable int id, @PathVariable @Schema(example = "2 for Staff and 3 for Admin")int new_privilege) {
        try {
            return ok(userService.grantPermission(id, new_privilege));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
