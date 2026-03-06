package com.hospital.appointment_service.dto;


import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String address;
}
