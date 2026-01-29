package com.hospital.feedback_service.service;

import com.hospital.feedback_service.dto.FeedbackResponseDTO;
import com.hospital.feedback_service.model.Feedback;

import java.util.List;

public interface FeedbackService {

    Feedback createFeedback(Feedback feedback);


    List<FeedbackResponseDTO> getAllFeedbacksWithDetails();


    FeedbackResponseDTO getFeedbackById(Long id);


    Feedback updateFeedback(Long id, Feedback feedback);


    void deleteFeedback(Long id);

}
