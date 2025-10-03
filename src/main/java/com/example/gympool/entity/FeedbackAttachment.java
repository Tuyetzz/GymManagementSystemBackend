package com.example.gympool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="feedback_attachments")
public class FeedbackAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    private String fileType;

    private Long fileSize;

    // Quan hệ với feedback
    @ManyToOne
    @JoinColumn(name = "feedback_id", nullable = false)
    @JsonIgnore
    private Feedback feedback;
}
