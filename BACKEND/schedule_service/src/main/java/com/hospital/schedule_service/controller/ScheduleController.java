package com.hospital.schedule_service.controller;

import com.hospital.schedule_service.dto.ResponseDTO;
import com.hospital.schedule_service.dto.ScheduleDTO;
import com.hospital.schedule_service.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/save")
    public ResponseDTO saveSchedule(ScheduleDTO scheduleDTO) {
        return scheduleService.saveSchedule(scheduleDTO);
    }

//    @PostMapping("save")
//    public ResponseEntity<ResponseDTO> saveSchedules(@RequestBody ScheduleDTO scheduleDTO) {
//        return scheduleService.saveSchedule(scheduleDTO);
//    }
}
