package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule_pattern")
public class SchedulePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String daysOfWeek; // Ví dụ: "MONDAY,WEDNESDAY,FRIDAY" (lưu dạng chuỗi)

    @Column(nullable = false)
    private LocalTime timeStart;

    @Column(nullable = false)
    private LocalTime timeEnd;

    @Column(nullable = false)
    private LocalDate classStartDate; // Bắt đầu áp dụng từ ngày

    @Column(nullable = false)
    private LocalDate classEndDate;   // Kết thúc áp dụng vào ngày
}