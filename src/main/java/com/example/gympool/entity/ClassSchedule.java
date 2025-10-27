package com.example.gympool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(length = 20)
    private String status;   //"OPEN", "CLOSED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "classtemplate_id", nullable = true)
    @JsonBackReference
    private ClassTemplate classTemplate;

    @ManyToOne
    @JoinColumn(name = "schedule_pattern_id", nullable = true)
    private SchedulePattern schedulePattern;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    private Room room;
}
