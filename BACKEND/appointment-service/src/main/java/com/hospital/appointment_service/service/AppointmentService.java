package com.hospital.appointment_service.service;

import com.hospital.appointment_service.client.DoctorClient;
import com.hospital.appointment_service.client.ScheduleClient;
import com.hospital.appointment_service.client.UserClient;
import com.hospital.appointment_service.model.Appointment;
import com.hospital.appointment_service.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserClient patientClient;
    private final DoctorClient doctorClient;
    private final ScheduleClient scheduleClient;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserClient patientClient,
                              DoctorClient doctorClient,
                              ScheduleClient scheduleClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
        this.scheduleClient = scheduleClient;
    }

    public Appointment createAppointment(Appointment appointment) {
        try {
            System.out.println("Checking Patient ID: " + appointment.getPatientId());
            patientClient.getPatientById(appointment.getPatientId());

            System.out.println("Checking Doctor ID: " + appointment.getDoctorId());
            doctorClient.getDoctorById(appointment.getDoctorId());

            if (appointment.getStatus() == null) {
                appointment.setStatus("PENDING");
            }

            return appointmentRepository.save(appointment);

        } catch (Exception e) {
            System.err.println("Communication Error: " + e.getMessage());
            throw new RuntimeException("Microservice Error: " + e.getMessage());
        }
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // ✅ Patient ID by appointments - frontend call කරන endpoint
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment updateAppointment(Long id, Appointment details) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointment.setPatientId(details.getPatientId());
            appointment.setDoctorId(details.getDoctorId());
            appointment.setScheduleId(details.getScheduleId());
            appointment.setAppointmentTime(details.getAppointmentTime());
            appointment.setReason(details.getReason());
            appointment.setStatus(details.getStatus());
            return appointmentRepository.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }
}