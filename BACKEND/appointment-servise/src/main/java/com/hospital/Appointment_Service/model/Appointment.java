package com.hospital.Appointment_Service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data // මේකෙන් තමයි Getter/Setter ඔක්කොම හදන්නේ
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // මෙන්න මේ fields ටික හරියටම තියෙන්න ඕනේ
    private Long patientId;
    private Long doctorId;
    private Long scheduleId;

    private String status; // PENDING, CONFIRMED, etc.
    private LocalDateTime appointmentTime;
    private String reason;
}