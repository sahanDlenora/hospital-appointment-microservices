package com.hospital.doctor_service.service;

import com.hospital.doctor_service.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface DoctorService {

    Doctor createDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long id);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);
}
