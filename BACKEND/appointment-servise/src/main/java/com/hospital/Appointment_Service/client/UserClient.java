package com.hospitalManagement.Appointment_Service.client;

import com.hospitalManagement.Appointment_Service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDTO getPatientById(@PathVariable("id") Long id);
}
