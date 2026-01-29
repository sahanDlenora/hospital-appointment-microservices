package com.hospital.feedback_service.service;

import com.hospital.feedback_service.client.DoctorClient;
import com.hospital.feedback_service.client.PatientClient;
import com.hospital.feedback_service.dto.DoctorDTO;
import com.hospital.feedback_service.dto.FeedbackResponseDTO;
import com.hospital.feedback_service.dto.PatientDTO;
import com.hospital.feedback_service.model.Feedback;
import com.hospital.feedback_service.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    private final FeedbackRepository feedbackRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public FeedbackServiceImpl(
            FeedbackRepository feedbackRepository,
            PatientClient patientClient,
            DoctorClient doctorClient
    ) {
        this.feedbackRepository = feedbackRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }


    @Override
    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }


    @Override
    public List<FeedbackResponseDTO> getAllFeedbacksWithDetails() {

        List<Feedback> feedbacks = feedbackRepository.findAll();

        return feedbacks.stream().map(feedback -> {
            PatientDTO patient = patientClient.getPatientById(feedback.getPatientId());
            DoctorDTO doctor = doctorClient.getDoctorById(feedback.getDoctorId());

            FeedbackResponseDTO dto = new FeedbackResponseDTO();
            dto.setFeedbackId(feedback.getId());
            dto.setRating(feedback.getRating());
            dto.setComment(feedback.getComment());
            dto.setPatient(patient);
            dto.setDoctor(doctor);

            return dto;
        }).toList();
    }


    @Override
    public FeedbackResponseDTO getFeedbackById(Long id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        PatientDTO patient = patientClient.getPatientById(feedback.getPatientId());
        DoctorDTO doctor = doctorClient.getDoctorById(feedback.getDoctorId());

        FeedbackResponseDTO response = new FeedbackResponseDTO();
        response.setFeedbackId(feedback.getId());
        response.setRating(feedback.getRating());
        response.setComment(feedback.getComment());
        response.setPatient(patient);
        response.setDoctor(doctor);

        return response;
    }


    @Override
    public Feedback updateFeedback(Long id, Feedback feedback) {

        Feedback existing = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        existing.setRating(feedback.getRating());
        existing.setComment(feedback.getComment());

        return feedbackRepository.save(existing);
    }


    @Override
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

}
