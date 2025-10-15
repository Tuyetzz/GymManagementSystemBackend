package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime; //buoi hoc thuc te

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;   //buoi hoc thuc te

    @Column(nullable = false, length = 100)
    private String location;

    @Column(length = 20)
    private String status;   //"OPEN", "CLOSED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "class_type_id", nullable = false)
    private ClassTemplate classTemplate;

    @ManyToOne
    @JoinColumn(name = "schedule_pattern_id", nullable = true)
    private SchedulePattern schedulePattern;
}
