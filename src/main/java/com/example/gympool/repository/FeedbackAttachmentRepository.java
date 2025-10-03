package com.example.gympool.repository;

import com.example.gympool.entity.FeedbackAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackAttachmentRepository extends JpaRepository<FeedbackAttachment, Long> {
    List<FeedbackAttachment> findByFeedbackId(Long feedbackId);
}
