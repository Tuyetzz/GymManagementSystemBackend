package com.example.gympool.controller;

import com.example.gympool.entity.TrainingSession;
import com.example.gympool.entity.TrainingSession;
import com.example.gympool.service.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainingsession")
@RequiredArgsConstructor
public class TrainingSessionController {
    private final TrainingSessionService trainingSessionService;
    @GetMapping()
    public List<TrainingSession> findAll() {
        return trainingSessionService.getAllTrainingSession();
    }
    @GetMapping("/{id}")
    public TrainingSession findTrainingSessionById(@PathVariable("id") Long id) {
        return trainingSessionService.getTrainingSessionById(id);
    }
    @GetMapping("/member")
    public TrainingSession findTrainingSessionByCustomerName(@RequestParam("name") String name) {
        return trainingSessionService.getTrainingSessionByCustomerName(name);
    }
    @GetMapping("/staff")
    public TrainingSession findTrainingSessionByStaffName(@RequestParam("name") String name) {
        return trainingSessionService.getTrainingSessionByStaffName(name);
    }
    @PostMapping()
    public void addTrainingSession(@RequestBody TrainingSession TrainingSession) {
        trainingSessionService.addTrainingSession(TrainingSession);
    }


}
