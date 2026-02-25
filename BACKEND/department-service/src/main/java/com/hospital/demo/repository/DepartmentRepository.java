package com.hospital.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
