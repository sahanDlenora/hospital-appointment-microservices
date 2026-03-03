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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleList = new ArrayList<>();
        List<Schedule> all = scheduleRepository.findAll();
        all.stream().forEach(data->{
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setId(data.getId());
            scheduleDTO.setDoctorId(data.getDoctorId());
            scheduleDTO.setDate(data.getDate());
            scheduleDTO.setStartTime(data.getStartTime());
            scheduleDTO.setEndTime(data.getEndTime());
            scheduleList.add(scheduleDTO);
        });
        return scheduleList;
    }

    @Override
    public ResponseDTO updateSchedule(int id, ScheduleDTO scheduleDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();

            schedule.setDoctorId(scheduleDTO.getDoctorId());
            schedule.setDate(scheduleDTO.getDate());
            schedule.setStartTime(scheduleDTO.getStartTime());
            schedule.setEndTime(scheduleDTO.getEndTime());

            scheduleRepository.save(schedule);

            responseDTO.setMessage("Schedule updated successfully");
            responseDTO.setStatus(String.valueOf(HttpStatus.OK));
            responseDTO.setData(scheduleDTO);
        } else {
            responseDTO.setMessage("Schedule not found");
            responseDTO.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO deleteSchedule(int id) {
        ResponseDTO responseDTO = new ResponseDTO();

        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            responseDTO.setMessage("Schedule deleted successfully");
            responseDTO.setStatus(String.valueOf(HttpStatus.OK));
        } else {
            responseDTO.setMessage("Schedule not found");
            responseDTO.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
        }

        return responseDTO;
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
