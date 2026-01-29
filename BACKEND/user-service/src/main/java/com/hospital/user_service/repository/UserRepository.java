package com.hospital.user_service.repository;

import com.hospital.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // All basic CRUD operations are already available
}
