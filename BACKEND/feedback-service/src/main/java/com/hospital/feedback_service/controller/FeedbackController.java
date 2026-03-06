package com.hospital.feedback_service.controller;

import com.hospital.feedback_service.dto.FeedbackResponseDTO;
import com.hospital.feedback_service.model.Feedback;
import com.hospital.feedback_service.service.FeedbackService;
import com.hospital.feedback_service.util.JwtUtil;
import com.hospital.feedback_service.util.RoleUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService service;
    private final JwtUtil jwtUtil;

    public FeedbackController(FeedbackService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Feedback create(@RequestBody Feedback feedback,
                           @RequestHeader("Authorization") String authHeader) {

        // Extract token
        String token = authHeader.substring(7); // remove "Bearer "

        // Extract userId from token
        Long patientId = jwtUtil.extractUserId(token);

        // Set it on the feedback — ignore whatever frontend sends
        feedback.setPatientId(patientId);

        return service.createFeedback(feedback);
    }

    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id,
                           @RequestBody Feedback feedback,
                           @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);
        RoleUtil.checkAdmin(role);

        return service.updateFeedback(id, feedback);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String role = jwtUtil.extractRole(token);
        RoleUtil.checkAdmin(role);

        service.deleteFeedback(id);
    }

    @GetMapping
    public List<FeedbackResponseDTO> getAll() {
        return service.getAllFeedbacksWithDetails();
    }

    @GetMapping("/{id}")
    public FeedbackResponseDTO getFeedback(@PathVariable Long id) {
        return service.getFeedbackById(id);
    }
}
