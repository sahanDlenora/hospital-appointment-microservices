package com.hospital.feedback_service.service;

import com.hospital.feedback_service.model.Feedback;
import com.hospital.feedback_service.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    private final FeedbackRepository repository;


    public FeedbackServiceImpl(FeedbackRepository repository) {
        this.repository = repository;
    }


    @Override
    public Feedback createFeedback(Feedback feedback) {
        return repository.save(feedback);
    }


    @Override
    public List<Feedback> getAllFeedbacks() {
        return repository.findAll();
    }


    @Override
    public Feedback getFeedbackById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }


    @Override
    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback existing = getFeedbackById(id);
        existing.setRating(feedback.getRating());
        existing.setComment(feedback.getComment());
        return repository.save(existing);
    }


    @Override
    public void deleteFeedback(Long id) {
        repository.deleteById(id);
    }

}
