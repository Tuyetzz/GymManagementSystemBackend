package com.example.gympool.service.impl;

import com.example.gympool.entity.Feedback;
import com.example.gympool.entity.FeedbackAttachment;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.FeedbackAttachmentRepository;
import com.example.gympool.repository.FeedbackRepository;
import com.example.gympool.repository.MemberRepository;
import com.example.gympool.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackAttachmentRepository feedbackAttachmentRepository;
    private final MemberRepository memberRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               FeedbackAttachmentRepository feedbackAttachmentRepository,
                               MemberRepository memberRepository) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackAttachmentRepository = feedbackAttachmentRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Feedback create(Feedback feedback) {
        // B1: check member tồn tại
        if (feedback.getMember() == null || feedback.getMember().getId() == null) {
            throw new RuntimeException("Member id is required");
        }

        Member member = memberRepository.findById(feedback.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + feedback.getMember().getId()));
        feedback.setMember(member);

        // B2: lưu feedback
        Feedback savedFeedback = feedbackRepository.save(feedback);

        // B3: nếu có attachments thì lưu
        if (feedback.getAttachments() != null && !feedback.getAttachments().isEmpty()) {
            for (FeedbackAttachment attachment : feedback.getAttachments()) {
                attachment.setFeedback(savedFeedback);
                feedbackAttachmentRepository.save(attachment);
            }
        }

        return savedFeedback;
    }

    @Override
    public void delete(Feedback feedback) {
        feedbackRepository.delete(feedback);
    }

    @Override
    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
}
