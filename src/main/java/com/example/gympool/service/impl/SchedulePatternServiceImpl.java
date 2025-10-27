package com.example.gympool.service.impl;

import com.example.gympool.entity.SchedulePattern;
import com.example.gympool.repository.SchedulePatternRepository;
import com.example.gympool.service.SchedulePatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchedulePatternServiceImpl implements SchedulePatternService {

    private final SchedulePatternRepository schedulePatternRepository;

    @Override
    public SchedulePattern createSchedulePattern(SchedulePattern schedulePattern) {
        return schedulePatternRepository.save(schedulePattern);
    }

    @Override
    public List<SchedulePattern> getAllSchedulePatterns() {
        return schedulePatternRepository.findAll();
    }

    @Override
    public Optional<SchedulePattern> getSchedulePatternById(Long id) {
        return schedulePatternRepository.findById(id);
    }

    @Override
    public SchedulePattern updateSchedulePattern(Long id, SchedulePattern schedulePatternDetails) {
        SchedulePattern existingPattern = schedulePatternRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SchedulePattern not found with id: " + id));

        existingPattern.setDaysOfWeek(schedulePatternDetails.getDaysOfWeek());
        existingPattern.setTimeStart(schedulePatternDetails.getTimeStart());
        existingPattern.setTimeEnd(schedulePatternDetails.getTimeEnd());
        existingPattern.setClassStartDate(schedulePatternDetails.getClassStartDate());
        existingPattern.setClassEndDate(schedulePatternDetails.getClassEndDate());

        return schedulePatternRepository.save(existingPattern);
    }

    @Override
    public void deleteSchedulePattern(Long id) {
        if (!schedulePatternRepository.existsById(id)) {
            throw new RuntimeException("SchedulePattern not found with id: " + id);
        }
        schedulePatternRepository.deleteById(id);
    }
}
