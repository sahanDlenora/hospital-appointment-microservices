package com.hospital.doctor_service.service;

import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.model.Doctor;

import java.util.List;

public interface DoctorService {
    //Creates a new doctor in the system.
    DoctorResponseDTO createDoctor(Doctor doctor);
    //Retrieves all doctors
    List<DoctorResponseDTO> getAllDoctors();
    //Retrieves  doctors by id
    DoctorResponseDTO getDoctorById(Long id);
    //Updates an existing doctor's details
    Doctor updateDoctor(Long id, Doctor doctor);
    //Delete doctor from the system
    void deleteDoctor(Long id);
}