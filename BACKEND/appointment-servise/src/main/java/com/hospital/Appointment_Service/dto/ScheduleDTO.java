package com.hospitalManagement.Appointment_Service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDTO {
    private Integer id;
    private Integer doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
