package com.example.gympool.service;

import com.example.gympool.entity.ClassSchedule;

import java.util.List;
import java.util.Optional;

public interface ClassScheduleService {
    List<ClassSchedule> getAll();
    Optional<ClassSchedule> getById(Long id);
    ClassSchedule create(ClassSchedule classSchedule);
    ClassSchedule update(Long id, ClassSchedule classSchedule);
    void delete(Long id);
}
