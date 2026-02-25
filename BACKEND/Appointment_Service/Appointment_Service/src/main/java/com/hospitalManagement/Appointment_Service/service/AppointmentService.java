package com.hospitalManagement.Appointment_Service.service;


import com.hospitalManagement.Appointment_Service.model.Appointment;
import com.hospitalManagement.Appointment_Service.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
        private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getStatus() == null) {
            appointment.setStatus("PENDING");
        }
        return appointmentRepository.save(appointment);
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
