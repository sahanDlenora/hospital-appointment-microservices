package com.hospital.schedule_service.service;

import com.hospital.schedule_service.dto.ResponseDTO;
import com.hospital.schedule_service.dto.ScheduleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {
    ResponseDTO saveSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getAllSchedules();
    ResponseDTO updateSchedule(long id, ScheduleDTO scheduleDTO);
    ResponseDTO deleteSchedule(long id);
}
