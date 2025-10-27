package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "follow_class")
public class FollowClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date followDate;

    @ManyToOne
    @JoinColumn(name = "class_template_id", nullable = false)
    private ClassTemplate classTemplate;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
