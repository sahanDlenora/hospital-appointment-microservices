package com.hospital.schedule_service.service;

import com.hospital.schedule_service.dto.ResponseDTO;
import com.hospital.schedule_service.dto.ScheduleDTO;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
//    public ResponseEntity<ResponseDTO> saveSchedule(ScheduleDTO scheduleDTO);
    public ResponseDTO saveSchedule(ScheduleDTO scheduleDTO);
}
