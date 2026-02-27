package com.hospital.doctor_service.service.Implementation;

import com.hospital.doctor_service.client.DepartmentClient;
import com.hospital.doctor_service.dto.DepartmentDTO;
import com.hospital.doctor_service.dto.DoctorResponseDTO;
import com.hospital.doctor_service.exception.ResourceNotFoundException;
import com.hospital.doctor_service.model.Doctor;
import com.hospital.doctor_service.repository.DoctorRepository;
import com.hospital.doctor_service.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImplementation implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentClient departmentClient;

    public DoctorServiceImplementation(DoctorRepository doctorRepository,
                                       DepartmentClient departmentClient) {
        this.doctorRepository = doctorRepository;
        this.departmentClient = departmentClient;
    }

    @Override
    public DoctorResponseDTO createDoctor(Doctor doctor) {
        // Validate department exists
        DepartmentDTO department = departmentClient.getDepartmentById(doctor.getDepartmentId());

        // Save doctor
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Build response with department
        return buildDoctorResponse(savedDoctor, department);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        // Map each doctor to DoctorResponseDTO with full department details
        return doctors.stream()
                .map(doctor -> {
                    DepartmentDTO department = departmentClient.getDepartmentById(doctor.getDepartmentId());
                    return buildDoctorResponse(doctor, department);
                })
                .toList();
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        DepartmentDTO department = departmentClient.getDepartmentById(doctor.getDepartmentId());

        return buildDoctorResponse(doctor, department);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setDepartmentId(doctor.getDepartmentId());

        return doctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }

    // Helper method to build DoctorResponseDTO with department details
    private DoctorResponseDTO buildDoctorResponse(Doctor doctor, DepartmentDTO department) {
        DoctorResponseDTO response = new DoctorResponseDTO();
        response.setId(doctor.getDoctorId());  // Use getId() if your field is 'id'
        response.setName(doctor.getName());
        response.setSpecialization(doctor.getSpecialization());
        response.setDepartment(department); // Full department DTO

        return response;
    }
}