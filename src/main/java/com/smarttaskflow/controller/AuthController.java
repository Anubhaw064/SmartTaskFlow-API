package com.smarttaskflow.controller;

import com.smarttaskflow.entity.User;
import com.smarttaskflow.security.JwtUtil;
import com.smarttaskflow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loggedUser = authService.login(user.getUsername(), user.getPassword());
        return jwtUtil.generateToken(loggedUser.getUsername());
    }
}