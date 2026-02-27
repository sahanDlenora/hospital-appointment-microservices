package com.hospital.doctor_service.service;

import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.model.Doctor;

import java.util.List;

public interface DoctorService {

    DoctorResponseDTO createDoctor(Doctor doctor);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO getDoctorById(Long id);

    Doctor updateDoctor(Long id, Doctor doctor);

    void deleteDoctor(Long id);
}