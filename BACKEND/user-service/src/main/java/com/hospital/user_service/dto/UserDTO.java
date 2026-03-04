package com.hospital.user_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String address;
}
