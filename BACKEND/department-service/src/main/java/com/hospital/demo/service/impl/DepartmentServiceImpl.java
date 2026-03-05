package com.hospital.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.demo.entity.Department;
import com.hospital.demo.repository.DepartmentRepository;
import com.hospital.demo.service.DepartmentService;
import com.hospital.demo.exception.ResourceNotFoundException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Override
    public Department saveDepartment(Department department) {
        return repository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Override
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existing = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        existing.setDepartmentName(department.getDepartmentName());
        existing.setDepartmentCode(department.getDepartmentCode());
        existing.setDepartmentDescription(department.getDepartmentDescription());
        return repository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        repository.delete(department);
    }
}

