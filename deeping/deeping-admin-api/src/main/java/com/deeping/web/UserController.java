package com.deeping.web;

import com.deeping.domain.User;
import com.deeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

    @RestController
    public class UserController {

        @Autowired
        private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello-world";
    }

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();

        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        String phone = resource.getPhone();
        String name = resource.getName();

        User user = userService.addUser(phone,name);
        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public String update(@PathVariable Long id, @RequestBody User resource){
        String phone = resource.getPhone();
        String name = resource.getName();
        Long level = resource.getLevel();

        userService.updateUser(id, phone, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deactiveUser(id);

        return "{}";
    }
}
