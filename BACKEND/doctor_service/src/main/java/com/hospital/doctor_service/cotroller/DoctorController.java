package com.hospital.doctor_service.cotroller;

import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.model.Doctor;
import com.hospital.doctor_service.service.DoctorService;
import com.hospital.doctor_service.util.RoleUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller for handling Doctor-related API requests

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    // Inject DoctorService
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Create a new doctor
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(
            @RequestBody Doctor doctor,
            @RequestHeader("role") String role) {

        RoleUtil.checkAdmin(role);

        return ResponseEntity.ok(doctorService.createDoctor(doctor));
    }

    // Get all doctors with full department info
    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // Get doctor by ID with full department info
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    // Update a doctor by ID
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor,
            @RequestHeader("role") String role) {

        RoleUtil.checkAdmin(role);

        return ResponseEntity.ok(doctorService.updateDoctor(id, doctor));
    }

    // Delete a doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable Long id,
            @RequestHeader("role") String role) {

        RoleUtil.checkAdmin(role);

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok("Doctor deleted successfully");
    }
}