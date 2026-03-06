package com.hospital.schedule_service.controller;

import com.hospital.schedule_service.dto.ResponseDTO;
import com.hospital.schedule_service.dto.ScheduleDTO;
import com.hospital.schedule_service.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseDTO saveSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        return scheduleService.saveSchedule(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PutMapping("/{id}")
    public ResponseDTO updateSchedule(@PathVariable int id,
                                      @RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.updateSchedule(id, scheduleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteSchedule(@PathVariable int id) {
        return scheduleService.deleteSchedule(id);
    }

}
