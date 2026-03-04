package com.hospital.demo.controller;

import java.util.List;

import com.hospital.demo.util.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.demo.entity.Department;
import com.hospital.demo.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    // CREATE
    @PostMapping
    public ResponseEntity<Department> saveDepartment(
            @RequestBody Department department,
            @RequestHeader("role") String role){

        RoleUtil.checkAdmin(role);

        return ResponseEntity.ok(service.saveDepartment(department));
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id){
        return ResponseEntity.ok(service.getDepartmentById(id));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Department>> getAll(){
        return ResponseEntity.ok(service.getAllDepartments());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department department,
            @RequestHeader("role") String role){

        RoleUtil.checkAdmin(role);

        return ResponseEntity.ok(service.updateDepartment(id, department));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable Long id,
            @RequestHeader("role") String role){

        RoleUtil.checkAdmin(role);

        service.deleteDepartment(id);

        return ResponseEntity.ok("Department deleted successfully");
    }
}
