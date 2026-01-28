package com.hospital.feedback_service.controller;

import com.hospital.feedback_service.model.Feedback;
import com.hospital.feedback_service.service.FeedbackService;
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
    public List<Feedback> getAll() {
        return service.getAllFeedbacks();
    }


    @GetMapping("/{id}")
    public Feedback getById(@PathVariable Long id) {
        return service.getFeedbackById(id);
    }


    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id, @RequestBody Feedback feedback) {
        return service.updateFeedback(id, feedback);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteFeedback(id);
    }
}
