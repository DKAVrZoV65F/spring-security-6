package org.spring.security6.controller;

import lombok.AllArgsConstructor;
import org.spring.security6.entity.User;
import org.spring.security6.repository.UserRepository;
import org.spring.security6.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/loginn")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }
}
