package com.hospitalManagement.Appointment_Service.dto;

import lombok.Data;

@Data

public class DoctorDTO {
    private Long doctorId;
    private String name;
    private String specialization;
}
