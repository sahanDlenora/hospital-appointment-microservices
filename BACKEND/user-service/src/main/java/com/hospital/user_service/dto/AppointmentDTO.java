package com.hospital.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Generates default constructor
@AllArgsConstructor // Generates constructor with all fields
public class AppointmentDTO {
    private Long id;
    private Long userId;
    private Long doctorId;
    private String appointmentDate;
}