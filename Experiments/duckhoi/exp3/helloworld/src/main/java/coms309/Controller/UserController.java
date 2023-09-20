package coms309.Controller;

import coms309.Models.User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Repeatable;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class UserController {
    @GetMapping("/users/id")
    public @ResponseBody User getUserById(@PathVariable UUID id) {
        return null;
    }
    @GetMapping("/users/email")
    public @ResponseBody User getUserByEmail(@PathVariable String email) {
        return null;
    }
    @PutMapping("/users/id")
    public @ResponseBody void updateUser(@PathVariable UUID id, @RequestBody User user) {

    }

    @PostMapping("/users")
    public @ResponseBody void createNewUser(@RequestBody User user) {
        User new_user = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserName(), user.getAddress());
    }
}
