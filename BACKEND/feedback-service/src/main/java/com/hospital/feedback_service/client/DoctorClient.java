package com.hospital.feedback_service.client;

import com.hospital.feedback_service.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("api/doctors/{id}")
    DoctorDTO getDoctorById(@PathVariable Long id);
}
