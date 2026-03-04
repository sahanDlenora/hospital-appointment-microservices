package com.hospital.feedback_service.controller;

import com.hospital.feedback_service.dto.FeedbackResponseDTO;
import com.hospital.feedback_service.model.Feedback;
import com.hospital.feedback_service.service.FeedbackService;
import com.hospital.feedback_service.util.RoleUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService service;


    public FeedbackController(FeedbackService service) {
        this.service = service;
    }


    @PostMapping
    public Feedback create(@RequestBody Feedback feedback) {
        return service.createFeedback(feedback);
    }


    @GetMapping
    public List<FeedbackResponseDTO> getAll() {
        return service.getAllFeedbacksWithDetails();
    }


    @GetMapping("/{id}")
    public FeedbackResponseDTO getFeedback(@PathVariable Long id) {
        return service.getFeedbackById(id);
    }


    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id,
                           @RequestBody Feedback feedback,
                           @RequestHeader("role") String role) {

        RoleUtil.checkAdmin(role);

        return service.updateFeedback(id, feedback);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestHeader("role") String role) {

        RoleUtil.checkAdmin(role);

        service.deleteFeedback(id);
    }


}
