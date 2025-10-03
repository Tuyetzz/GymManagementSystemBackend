package com.example.gympool.service.impl;

import com.example.gympool.entity.Feedback;
import com.example.gympool.entity.FeedbackAttachment;
import com.example.gympool.repository.FeedbackAttachmentRepository;
import com.example.gympool.repository.FeedbackRepository;
import com.example.gympool.service.FeedbackAttachmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackAttachmentServiceImpl implements FeedbackAttachmentService {

    private final FeedbackAttachmentRepository feedbackAttachmentRepository;
    private final FeedbackRepository feedbackRepository;

    public FeedbackAttachmentServiceImpl(FeedbackAttachmentRepository feedbackAttachmentRepository,
                                         FeedbackRepository feedbackRepository) {
        this.feedbackAttachmentRepository = feedbackAttachmentRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public FeedbackAttachment create(FeedbackAttachment attachment, Long feedbackId) {
        // Tìm feedback
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id " + feedbackId));

        // Gán feedback vào attachment
        attachment.setFeedback(feedback);

        return feedbackAttachmentRepository.save(attachment);
    }

    @Override
    public void delete(FeedbackAttachment attachment) {
        feedbackAttachmentRepository.delete(attachment);
    }

    @Override
    public FeedbackAttachment findById(Long id) {
        return feedbackAttachmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<FeedbackAttachment> findAll() {
        return feedbackAttachmentRepository.findAll();
    }

    @Override
    public List<FeedbackAttachment> findByFeedbackId(Long feedbackId) {
        return feedbackAttachmentRepository.findByFeedbackId(feedbackId);
    }
}
