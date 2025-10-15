package com.example.gympool.service;
import com.example.gympool.entity.SchedulePattern;

import java.util.List;
import java.util.Optional;

public interface SchedulePatternService {
    SchedulePattern createSchedulePattern(SchedulePattern schedulePattern);
    List<SchedulePattern> getAllSchedulePatterns();
    Optional<SchedulePattern> getSchedulePatternById(Long id);
    SchedulePattern updateSchedulePattern(Long id, SchedulePattern schedulePatternDetails);
    void deleteSchedulePattern(Long id);
}
