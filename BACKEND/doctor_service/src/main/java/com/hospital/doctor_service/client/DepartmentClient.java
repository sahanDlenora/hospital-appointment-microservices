package com.hospital.doctor_service.client;

import com.hospital.doctor_service.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign client for calling the Department Service APIs
@FeignClient(name = "department-service")
public interface DepartmentClient {

    // Get department details by department ID from department-service
    @GetMapping("/api/departments/{id}")
    DepartmentDTO getDepartmentById(@PathVariable Long id);
}