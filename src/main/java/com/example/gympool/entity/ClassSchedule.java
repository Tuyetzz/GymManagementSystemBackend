package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classschedule")
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;

    @Column(length = 20)
    private String status;   //"OPEN", "CLOSED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "class_type_id", nullable = false)
    private ClassTemplate classTemplate;

    @ManyToOne
    @JoinColumn(name = "schedule_pattern_id", nullable = true)
    private SchedulePattern schedulePattern;

    //TODO : BE : Delete batch, get week, add batch
    //TODO : FE : Add (special class(no pattern) - course(no attribute))
}
