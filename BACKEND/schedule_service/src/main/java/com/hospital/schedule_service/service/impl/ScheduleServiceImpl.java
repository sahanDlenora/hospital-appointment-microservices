package com.hospital.schedule_service.service.impl;

import com.hospital.schedule_service.dto.ResponseDTO;
import com.hospital.schedule_service.dto.ScheduleDTO;
import com.hospital.schedule_service.model.Schedule;
import com.hospital.schedule_service.repository.ScheduleRepository;
import com.hospital.schedule_service.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ResponseDTO saveSchedule(ScheduleDTO scheduleDTO) {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Schedule schedule = new Schedule();
            schedule.setDoctorId(scheduleDTO.getDoctorId());
            schedule.setDate(scheduleDTO.getDate());
            schedule.setStartTime(scheduleDTO.getStartTime());
            schedule.setEndTime(scheduleDTO.getEndTime());

            scheduleRepository.save(schedule);

            responseDTO.setMessage("Save schedule successfully");
            responseDTO.setStatus(String.valueOf(HttpStatus.CREATED));

            return responseDTO;
        } catch (Exception e) {
            log.error("Faild insert new schedule", e);
            responseDTO.setMessage("Faild insert new schedule");
            responseDTO.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
            return responseDTO;
        }

    }


//    @Override
//    public ResponseEntity<ResponseDTO> saveSchedule(ScheduleDTO scheduleDTO) {
//        Schedule schedule = new Schedule();
//        schedule.setDoctorId(scheduleDTO.getDoctorId());
//        schedule.setDate(scheduleDTO.getDate());
//        schedule.setStartTime(scheduleDTO.getStartTime());
//        schedule.setEndTime(scheduleDTO.getEndTime());
//        scheduleRepository.save(schedule);
//        return new ResponseEntity<>(ResponseDTO.builder()
//                .message("Save schedule successfully")
//                .responseCode(HttpStatus.CREATED)
//                .build(),HttpStatus.CREATED);
//    }
}
