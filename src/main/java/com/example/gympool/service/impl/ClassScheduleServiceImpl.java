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

    private final ClassScheduleRepository classScheduleRepository;
    private final ClassTemplateRepository classTemplateRepository;
    private final SchedulePatternRepository schedulePatternRepository;

    public ClassScheduleServiceImpl(ClassScheduleRepository classScheduleRepository, ClassTemplateRepository classTemplateRepository,  SchedulePatternRepository schedulePatternRepository) {
        this.classScheduleRepository = classScheduleRepository;
        this.classTemplateRepository = classTemplateRepository;
        this.schedulePatternRepository = schedulePatternRepository;
    }

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
                    existing.setLocation(classSchedule.getLocation());
                    existing.setStatus(classSchedule.getStatus());
                    existing.setClassTemplate(classSchedule.getClassTemplate());
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

        SchedulePattern pattern = schedulePatternRepository.findById(patternId)
                .orElseThrow(() -> new RuntimeException("Schedule pattern not found"));

        ClassTemplate classTemplate = classTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Class template not found"));

        // Parse danh sách ngày trong tuần
        List<DayOfWeek> days = Arrays.stream(pattern.getDaysOfWeek().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .toList();

        LocalDate patternDate = pattern.getClassStartDate();    //cua pattern
        List<ClassSchedule> generated = new ArrayList<>();

        while (!patternDate.isAfter(pattern.getClassEndDate())) {   //for (patternDate -> end date)
            if (days.contains(patternDate.getDayOfWeek())) {
                LocalDateTime start = LocalDateTime.of(patternDate, pattern.getTimeStart());    //gan ngay va thang
                LocalDateTime end = LocalDateTime.of(patternDate, pattern.getTimeEnd());

                ClassSchedule schedule = new ClassSchedule();   //lich hoc add batch -> add list
                schedule.setStartTime(start);   //start cua pattern
                schedule.setEndTime(end);       //end cua pattern

                schedule.setLocation(pattern.getLocation());
                schedule.setStatus("OPEN");


                schedule.setClassTemplate(classTemplate);
                schedule.setSchedulePattern(pattern);

                System.out.println(schedule);
                generated.add(schedule);
            }
            patternDate = patternDate.plusDays(1);
        }
//        return generated;
        return classScheduleRepository.saveAll(generated);
    }

}
