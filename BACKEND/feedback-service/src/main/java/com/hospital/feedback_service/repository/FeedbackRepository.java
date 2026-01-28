package com.hospital.feedback_service.repository;

import com.hospital.feedback_service.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
