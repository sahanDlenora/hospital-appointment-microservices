package com.hospital.user_service.client;

import com.hospital.user_service.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "appointment-service")
public interface AppointmentClient {
    @GetMapping("/appointment/patient/{userId}")
    List<AppointmentDTO> getAppointmentsByUser(@PathVariable("userId") Long userId);
}
