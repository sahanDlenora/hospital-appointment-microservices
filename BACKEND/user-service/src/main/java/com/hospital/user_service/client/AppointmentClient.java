package com.hospital.user_service.client;

import com.hospital.user_service.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "appointment-service", url = "http://localhost:8080")
public interface AppointmentClient {

    @GetMapping("/appointments/user/{userId}")
    List<AppointmentDTO> getAppointmentsByUser(@PathVariable Long userId);
}