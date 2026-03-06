package com.hospital.user_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.user_service.client.AppointmentClient;
import com.hospital.user_service.dto.AppointmentDTO;
import com.hospital.user_service.dto.UserDTO;
import com.hospital.user_service.entity.User;
import com.hospital.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AppointmentClient appointmentClient;
    private final ObjectMapper objectMapper;

    // CREATE
    public UserDTO createUser(User user) {

        User save = userRepository.save(user);
        return objectMapper.convertValue(save, UserDTO.class);
    }

    // READ ALL
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // READ BY ID
    public User getUserById(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public List<AppointmentDTO> getUserAppointments(Long userId) {
        return appointmentClient.getAppointmentsByUser(userId);
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
