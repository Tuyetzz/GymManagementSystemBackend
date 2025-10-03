package com.example.gympool.controller;

import com.example.gympool.entity.Feedback;
import com.example.gympool.entity.FeedbackAttachment;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.MemberRepository;
import com.example.gympool.service.FeedbackAttachmentService;
import com.example.gympool.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackAttachmentService feedbackAttachmentService;
    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<?> createFeedback(
            @RequestParam Integer rating,
            @RequestParam String comment,
            @RequestParam Long memberId,
            @RequestParam(required = false) MultipartFile[] images
    ) {
        try {
            // Tìm member
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

            // Tạo feedback
            Feedback feedback = new Feedback();
            feedback.setRating(rating);
            feedback.setComment(comment);
            feedback.setMember(member);
            Feedback savedFeedback = feedbackService.create(feedback);

            List<FeedbackAttachment> attachments = new ArrayList<>();

            // Nếu có upload ảnh thì lưu
            if (images != null && images.length > 0) {
                // Base path tuyệt đối
                String uploadDir = System.getProperty("user.dir") + File.separator +
                        "uploads" + File.separator + "feedback" + File.separator;
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        // Đặt tên file
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        String filePath = uploadDir + fileName;

                        // Lưu file vật lý
                        File dest = new File(filePath);
                        file.transferTo(dest);

                        // Tạo attachment
                        FeedbackAttachment attachment = new FeedbackAttachment();
                        attachment.setFilePath("uploads/feedback/" + fileName); // lưu relative để trả ra client
                        attachment.setFileType(file.getContentType());
                        attachment.setFileSize(file.getSize());

                        FeedbackAttachment savedAttachment =
                                feedbackAttachmentService.create(attachment, savedFeedback.getId());

                        attachments.add(savedAttachment);
                    }
                }
            }

            return ResponseEntity.ok().body(savedFeedback);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while creating feedback: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        Feedback feedback = feedbackService.findById(id);
        feedbackService.delete(feedback);
        return ResponseEntity.ok().body("Feedback deleted successfully");
    }
}
