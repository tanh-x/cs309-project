//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coms309;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    String user = "";

    public TestController() {
    }

    @GetMapping({"/User"})
    public String getUser() {
        return this.user;
    }

    @PutMapping({"/User/{name}"})
    public void updateUser(@PathVariable String name) {
        if (name != null) {
            this.user = name;
        } else {
            throw new NullPointerException("So you want to go blank ha?");
        }
    }

    @DeleteMapping({"/User"})
    public void deleteUser() {
        this.user = "";
    }
}
