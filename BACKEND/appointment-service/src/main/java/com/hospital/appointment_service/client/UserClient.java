package com.hospital.appointment_service.client;

import com.hospital.appointment_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/{id}")
    UserDTO getPatientById(@PathVariable("id") Long id);
}