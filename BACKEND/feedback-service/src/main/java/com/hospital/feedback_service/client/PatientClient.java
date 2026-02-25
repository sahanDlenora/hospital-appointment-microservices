package com.hospital.feedback_service.client;

import com.hospital.feedback_service.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface PatientClient {

    @GetMapping("/users/{id}")
    PatientDTO getPatientById(@PathVariable Long id);

}
