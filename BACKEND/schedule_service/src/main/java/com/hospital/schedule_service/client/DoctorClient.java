package com.hospital.schedule_service.client;

import com.hospital.schedule_service.dto.DoctorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("/api/doctors/{id}")
    DoctorResponseDTO getDoctorById(@PathVariable Long id);
}