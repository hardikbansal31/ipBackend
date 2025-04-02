package com.task.taskagain.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import com.task.taskagain.model.User;
import com.task.taskagain.repository.UserRepository;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // ✅ Allow frontend requests
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')") // ✅ Only admins can fetch users
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
