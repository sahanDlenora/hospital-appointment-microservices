package com.hospitalManagement.Appointment_Service.service;

import com.hospitalManagement.Appointment_Service.client.DoctorClient;
import com.hospitalManagement.Appointment_Service.client.ScheduleClient;
import com.hospitalManagement.Appointment_Service.client.UserClient;
import com.hospitalManagement.Appointment_Service.model.Appointment;
import com.hospitalManagement.Appointment_Service.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    // 1. මේවා final නිසා අනිවාර්යයෙන්ම constructor එකක් මගින් initialize විය යුතුයි.
    private final AppointmentRepository appointmentRepository;
    private final UserClient patientClient;
    private final DoctorClient doctorClient;
    private final ScheduleClient scheduleClient;

    // 2. Lombok වැඩ නොකරන නිසා අපිම Constructor එක ලියමු (Manual Constructor Injection)
    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserClient patientClient,
                              DoctorClient doctorClient,
                              ScheduleClient scheduleClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
        this.scheduleClient = scheduleClient;
    }

    // Appointment එකක් create කරන ප්‍රධාන logic එක (OpenFeign භාවිතයෙන්)
    public Appointment createAppointment(Appointment appointment) {
        // Temporarily skip validation for testing
        // Patient validation
        // Object patient = patientClient.getPatientById(appointment.getPatientId());
        // if (patient == null) throw new RuntimeException("Patient not found!");

        // Doctor validation
        // Object doctor = doctorClient.getDoctorById(appointment.getDoctorId());
        // if (doctor == null) throw new RuntimeException("Doctor not found!");

        // Schedule validation
        // Object schedule = scheduleClient.getScheduleById(appointment.getScheduleId());
        // if (schedule == null) throw new RuntimeException("Schedule not available!");

        if (appointment.getStatus() == null) {
            appointment.setStatus("PENDING");
        }

        return appointmentRepository.save(appointment);
    }

    // අනෙකුත් CRUD මෙහෙයුම්
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}