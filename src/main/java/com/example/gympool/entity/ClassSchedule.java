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
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(length = 20)
    private String status;   //"OPEN", "CLOSED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name = "class_type_id", nullable = false)
    private ClassTemplate classTemplate;
}
