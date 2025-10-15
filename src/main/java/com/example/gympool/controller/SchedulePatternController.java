package com.example.gympool.controller;

import com.example.gympool.entity.SchedulePattern;
import com.example.gympool.service.SchedulePatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-patterns")
@RequiredArgsConstructor
public class SchedulePatternController {

    private final SchedulePatternService schedulePatternService;

    @PostMapping
    public ResponseEntity<SchedulePattern> createSchedulePattern(@RequestBody SchedulePattern schedulePattern) {
        SchedulePattern createdPattern = schedulePatternService.createSchedulePattern(schedulePattern);
        return new ResponseEntity<>(createdPattern, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchedulePattern>> getAllSchedulePatterns() {
        List<SchedulePattern> patterns = schedulePatternService.getAllSchedulePatterns();
        return ResponseEntity.ok(patterns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulePattern> getSchedulePatternById(@PathVariable Long id) {
        return schedulePatternService.getSchedulePatternById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchedulePattern> updateSchedulePattern(@PathVariable Long id, @RequestBody SchedulePattern schedulePatternDetails) {
        try {
            SchedulePattern updatedPattern = schedulePatternService.updateSchedulePattern(id, schedulePatternDetails);
            return ResponseEntity.ok(updatedPattern);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedulePattern(@PathVariable Long id) {
        try {
            schedulePatternService.deleteSchedulePattern(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}