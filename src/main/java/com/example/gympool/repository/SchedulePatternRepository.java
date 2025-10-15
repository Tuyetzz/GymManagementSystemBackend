package com.example.gympool.repository;

import com.example.gympool.entity.SchedulePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePatternRepository extends JpaRepository<SchedulePattern, Long> {
}
