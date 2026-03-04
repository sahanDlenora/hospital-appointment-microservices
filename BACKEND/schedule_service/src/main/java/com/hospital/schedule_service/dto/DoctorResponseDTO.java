package com.hospital.schedule_service.dto;

import lombok.Data;

@Data
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String specialization;
    private Object department;
}
