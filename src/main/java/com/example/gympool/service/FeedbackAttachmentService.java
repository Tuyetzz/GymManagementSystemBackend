package com.example.gympool.service;

import com.example.gympool.entity.FeedbackAttachment;

import java.util.List;

public interface FeedbackAttachmentService {
    FeedbackAttachment create(FeedbackAttachment attachment, Long feedbackId);
    void delete(FeedbackAttachment attachment);
    FeedbackAttachment findById(Long id);
    List<FeedbackAttachment> findAll();
    List<FeedbackAttachment> findByFeedbackId(Long feedbackId);
}
