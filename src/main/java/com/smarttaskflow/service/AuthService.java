
package com.smarttaskflow.service;

import com.smarttaskflow.entity.User;
import com.smarttaskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists");
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Registration conflicts with database constraints");
        }
    }

    public User login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user != null &&
                passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }
}
