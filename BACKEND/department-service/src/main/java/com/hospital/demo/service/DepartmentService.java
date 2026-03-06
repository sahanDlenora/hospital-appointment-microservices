package com.hospital.demo.service;

import java.util.List;
import com.hospital.demo.entity.Department;

public interface DepartmentService {

    Department saveDepartment(Department department);

    Department getDepartmentById(Long id);

    List<Department> getAllDepartments();

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);
}
