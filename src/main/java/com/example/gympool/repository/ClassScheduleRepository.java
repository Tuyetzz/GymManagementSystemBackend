package com.example.gympool.repository;

import com.example.gympool.entity.ClassSchedule;
import com.example.gympool.entity.ClassTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassTemplateId(Long classTemplateId);
    List<ClassSchedule> findByStatus(String status);
    List<ClassSchedule> findByStartTimeBetween(Date start, Date end);
}
