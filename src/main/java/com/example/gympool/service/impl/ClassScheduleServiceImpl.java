package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {

    @Autowired
    private final ClassScheduleRepository classScheduleRepository;

    public ClassScheduleServiceImpl(ClassScheduleRepository classScheduleRepository) {
        this.classScheduleRepository = classScheduleRepository;
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
}
