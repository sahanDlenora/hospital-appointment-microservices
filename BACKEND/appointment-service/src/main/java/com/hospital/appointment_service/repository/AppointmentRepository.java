package com.hospital.appointment_service.repository;

import com.hospital.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Patient ID by appointments find කරන්න
    List<Appointment> findByPatientId(Long patientId);

    // Doctor ID by appointments find කරන්න
    List<Appointment> findByDoctorId(Long doctorId);
}