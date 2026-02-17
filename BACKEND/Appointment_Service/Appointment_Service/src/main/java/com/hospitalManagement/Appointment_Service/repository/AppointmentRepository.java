package com.hospitalManagement.Appointment_Service.repository;

import com.hospitalManagement.Appointment_Service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

}
