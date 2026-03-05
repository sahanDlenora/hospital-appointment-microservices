package com.hospitalManagement.Appointment_Service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long patientId;
    private Long doctorId;
    private Long scheduleId;
    private LocalDateTime appointmentTime;
    private String reason;
    private String status;


    public Appointment(Long patientId, Long doctorId,Long scheduleId, LocalDateTime appointmentTime, String reason, String status) {

        this.patientId = patientId;
        this.doctorId = doctorId;
        this.scheduleId = scheduleId;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }

    @Override
    public String toString() {
      return "Appointment{" +
              "id=" + id +
            ",patientId=" + patientId +
            ",doctorId="  + doctorId +
            ",scheduleId=" + scheduleId +
            ",appointmentTime=" + appointmentTime +
            ",reason='" + reason + '\'' +
            ",status='" + status + '\'' +
      '}';

    }

}
