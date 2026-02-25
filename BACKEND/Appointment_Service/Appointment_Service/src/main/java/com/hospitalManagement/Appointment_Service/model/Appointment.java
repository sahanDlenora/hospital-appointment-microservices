package com.hospitalManagement.Appointment_Service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Appointment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long patientId;
    private Long doctorId;
    private Long scheduleId;
    private LocalDateTime appointmentTime;
    private String reason;
    private String status;

    public Appointment() {

    }

    public Appointment(Long patientId, Long doctorId,Long scheduleId, LocalDateTime appointmentTime, String reason, String status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.scheduleId = scheduleId;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }

    public Long getPatientId() {return patientId;}

    public void setPatientId(Long patientId) {this.patientId = patientId;}

    public Long getDoctorId() {return doctorId;}

    public void setDoctorId(Long doctorId) {this.doctorId = doctorId;}

    public Long getScheduleId() {return scheduleId;}

    public void setScheduleId(Long scheduleId) {this.scheduleId = scheduleId;}

    public LocalDateTime getAppointmentTime() {return appointmentTime;}

    public void setAppointmentTime(LocalDateTime appointmentTime) {this.appointmentTime = appointmentTime;}

    public String getReason() {return reason;}

    public void setReason(String reason) {this.reason = reason;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    @Override
    public String toString() {
      return "Appointment{" +
            ",patientId=" + patientId +
            ",doctorId="  + doctorId +
            ",scheduleId=" + scheduleId +
            ",appointmentTime=" + appointmentTime +
            ",reason='" + reason + '\'' +
            ",status='" + status + '\'' +
      '}';

    }

}
