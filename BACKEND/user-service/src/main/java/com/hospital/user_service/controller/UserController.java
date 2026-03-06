package com.hospital.user_service.controller;

import com.hospital.user_service.dto.AppointmentDTO;
import com.hospital.user_service.dto.UserDTO;
import com.hospital.user_service.entity.User;
import com.hospital.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public String health() {
        return "User Service is running";
    }

    // CREATE
    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // READ ALL
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/{userId}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = userService.getUserAppointments(userId);
        return ResponseEntity.ok(appointments);
    }
}
