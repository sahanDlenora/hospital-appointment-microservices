package com.hospital.appointment_service.dto;


import lombok.Data;

@Data

public class DoctorDTO {
    private Long doctorId;
    private String name;
    private String specialization;
}
