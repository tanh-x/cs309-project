//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coms309;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class TestController {
    String user = "";
    ArrayList<String> arr = new ArrayList<String>();

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

    @GetMapping("/User/Stuff")
    public ArrayList<String> getStuff() { return arr; }

    @PostMapping("/User/{stuff}")
    public ArrayList<String> addStuff(@PathVariable String stuff) {
        arr.add(stuff);
        return arr;
    }

    @DeleteMapping("/User/{stuff}")
    public boolean deleteStuff(@PathVariable String stuff) {
        if (!arr.contains(stuff)) {
            return false;
        }
        arr.remove(stuff);
        return true;
    }
}
