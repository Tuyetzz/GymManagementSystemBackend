package com.example.gympool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String daysOfWeek; // Ví dụ: "MONDAY,WEDNESDAY,FRIDAY" (lưu dạng chuỗi)

    private Date timeStart;
    private Date timeEnd;

    private Date classStartDate; // Bắt đầu áp dụng từ ngày
    private Date classEndDate;   // Kết thúc áp dụng vào ngày
}