package com.example.gympool.controller;

import com.example.gympool.entity.ClassSchedule;
import com.example.gympool.service.ClassScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classschedule")
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;

    public ClassScheduleController(ClassScheduleService classScheduleService) {
        this.classScheduleService = classScheduleService;
    }

    // CRUD
    @GetMapping
    public ResponseEntity<List<ClassSchedule>> getAll() {
        return ResponseEntity.ok(classScheduleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSchedule> getById(@PathVariable Long id) {
        return classScheduleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassSchedule> create(@RequestBody ClassSchedule classSchedule) {
        return ResponseEntity.ok(classScheduleService.create(classSchedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSchedule> update(@PathVariable Long id, @RequestBody ClassSchedule classSchedule) {
        return ResponseEntity.ok(classScheduleService.update(id, classSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-template/{templateId}")
    public ResponseEntity<List<ClassSchedule>> getByTemplateId(@PathVariable Long templateId) {
        List<ClassSchedule> schedules = classScheduleService.getByClassTemplateId(templateId);
        return ResponseEntity.ok(schedules);
    }
}
