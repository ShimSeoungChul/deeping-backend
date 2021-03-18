package com.deeping.web;

import com.deeping.domain.User;
import com.deeping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        String phone = resource.getPhone();
        String name = resource.getName();
        String password = resource.getPassword();

        User user = userService.registerUser(phone, name, password);

        String url = "/users/1004" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
