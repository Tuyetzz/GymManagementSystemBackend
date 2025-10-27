package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.ClassScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ClassScheduleServiceImpl implements ClassScheduleService {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;
    @Autowired
    private ClassTemplateRepository classTemplateRepository;
    @Autowired
    private SchedulePatternRepository schedulePatternRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<ClassSchedule> getAll() {
        return classScheduleRepository.findAll();
    }

    @Override
    public Optional<ClassSchedule> getById(Long id) {
        return classScheduleRepository.findById(id);
    }

    @Override
    public ClassSchedule create(ClassSchedule classSchedule) {
        classSchedule.setSchedulePattern(null);
        return classScheduleRepository.save(classSchedule);
    }


    @Override
    public ClassSchedule update(Long id, ClassSchedule classSchedule) {
        return classScheduleRepository.findById(id)
                .map(existing -> {
                    existing.setStartTime(classSchedule.getStartTime());
                    existing.setEndTime(classSchedule.getEndTime());
                    existing.setStatus(classSchedule.getStatus());
                    existing.setClassTemplate(classSchedule.getClassTemplate());
                    existing.setRoom(classSchedule.getRoom());
                    return classScheduleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ClassSchedule not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        classScheduleRepository.deleteById(id);
    }

    @Override
    public List<ClassSchedule> getByClassTemplateId(Long id) {
        return classScheduleRepository.findByClassTemplateId(id);
    }

    @Override
    @Transactional
    public List<ClassSchedule> generateSchedulesFromPattern(ClassSchedule classSchedule) {
        if (classSchedule.getSchedulePattern() == null || classSchedule.getSchedulePattern().getId() == null) {
            throw new IllegalArgumentException("Schedule pattern ID is required for batch generation");
        }

        Long patternId = classSchedule.getSchedulePattern().getId();
        Long templateId = classSchedule.getClassTemplate().getId();
        Long roomId = classSchedule.getRoom().getId();

        SchedulePattern pattern = schedulePatternRepository.findById(patternId)
                .orElseThrow(() -> new RuntimeException("Schedule pattern not found"));
        ClassTemplate classTemplate = classTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Class template not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        //check date 6 month
        LocalDate startDate = pattern.getClassStartDate();
        LocalDate endDate = pattern.getClassEndDate();

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The end date cannot be less than the start date.");
        }

        if (endDate.isAfter(startDate.plusMonths(6))) {
            throw new IllegalArgumentException("The calendar creation period must not exceed 6 months.");
        }

        List<DayOfWeek> days = Arrays.stream(pattern.getDaysOfWeek().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .toList();

        LocalDate patternDate = pattern.getClassStartDate();
        List<ClassSchedule> generated = new ArrayList<>();

        while (!patternDate.isAfter(pattern.getClassEndDate())) {
            if (days.contains(patternDate.getDayOfWeek())) {
                LocalDateTime start = LocalDateTime.of(patternDate, pattern.getTimeStart());
                LocalDateTime end = LocalDateTime.of(patternDate, pattern.getTimeEnd());

                // Lấy tất cả lịch cùng phòng trong ngày đó
                LocalDateTime startOfDay = patternDate.atStartOfDay();
                LocalDateTime endOfDay = patternDate.atTime(23, 59, 59);
                List<ClassSchedule> existingSchedules =
                        classScheduleRepository.findSchedulesForRoomAndDate(roomId, startOfDay, endOfDay);

                // Kiểm tra trùng thời gian
                boolean conflict = existingSchedules.stream().anyMatch(existing ->
                        !(end.isBefore(existing.getStartTime()) || start.isAfter(existing.getEndTime()))
                );

                if (conflict) {
                    throw new RuntimeException(String.format(
                            "Lịch trùng phòng! Phòng '%s' đã có lớp khác từ %s đến %s vào ngày %s",
                            room.getName(), start, end, patternDate
                    ));
                }

                ClassSchedule schedule = new ClassSchedule();
                schedule.setStartTime(start);
                schedule.setEndTime(end);
                schedule.setStatus("OPEN");
                schedule.setClassTemplate(classTemplate);
                schedule.setSchedulePattern(pattern);
                schedule.setRoom(room);

                generated.add(schedule);
            }
            patternDate = patternDate.plusDays(1);
        }

        return classScheduleRepository.saveAll(generated);
    }

}
