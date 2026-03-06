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
            // 1. Patient check
            System.out.println("Checking Patient ID: " + appointment.getPatientId());
            Object patient = patientClient.getPatientById(appointment.getPatientId());

            // 2. Doctor check
            System.out.println("Checking Doctor ID: " + appointment.getDoctorId());
            Object doctor = doctorClient.getDoctorById(appointment.getDoctorId());

            // 3. Schedule check
            System.out.println("Checking Schedule ID: " + appointment.getScheduleId());
            //Object schedule = scheduleClient.getScheduleById(appointment.getScheduleId());

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

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }
}