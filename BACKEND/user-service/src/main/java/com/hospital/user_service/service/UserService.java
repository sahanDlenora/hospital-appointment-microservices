package com.hospital.user_service.service;

import com.hospital.user_service.entity.User;
import com.hospital.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // READ ALL
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // UPDATE
    public User updateUser(Long id, User updatedUser) {
        User existing = userRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(updatedUser.getName());
            existing.setAge(updatedUser.getAge());
            existing.setPhone(updatedUser.getPhone());
            existing.setAddress(updatedUser.getAddress());
            return userRepository.save(existing);
        }
        return null;
    }

    // DELETE
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
