package com.hospitalManagement.Appointment_Service.client;

import com.hospitalManagement.Appointment_Service.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "schedule-service")
public interface ScheduleClient {
    @GetMapping("/schedules/{id}")
    ScheduleDTO getScheduleById(@PathVariable("id") Long id);
}
