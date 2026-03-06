package com.hospital.doctor_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Doctor {
    //declare the variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @Column(unique = true)
    private String email;

    private String phone;
    private Long departmentId; // reference to Department Service

    public Doctor() {
    }
    //constructor used to create a Doctor object.
    public Doctor(Long doctorId, String name, String specialization, String email, String phone,Long departmentId) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
    }

    //getters and setters
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //toString method
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }


}
