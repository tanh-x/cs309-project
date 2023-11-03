package cs309.backend.controllers;

import cs309.backend.jpa.entity.TestEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import cs309.backend.models.ChangePasswordData;
import cs309.backend.models.UpdateInfoData;
import cs309.backend.models.UserData;
import cs309.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/get-test-data/{id}")
    public TestEntity getTestDataEndpoint(@PathVariable int id) {
        System.out.println("Getting test data for id " + id);
        return userService.readTestTable(id);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UserData> getUserById(@PathVariable int id) {
        try {
            UserEntity user = userService.getUserByUid(id);
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
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

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


    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordData req, Principal user) {
        try {
            String res = userService.changePassword(req, user);
            return ok(res);
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @GetMapping("/principal")
    public Principal principal(Principal p) {
        return p;
    }

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

    @PutMapping("/grant/{id}/{new_privilege}")
    public ResponseEntity<String> grantPermission(@PathVariable int id, @PathVariable int new_privilege) {
        try {
            return ok(userService.grantPermission(id, new_privilege));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}
