package com.example.gympool.service;

import com.example.gympool.entity.TrainingSession;

import java.util.List;

public interface TrainingSessionService  {
    List<TrainingSession> getAllTrainingSession();
    TrainingSession getTrainingSessionById(Long id);
    TrainingSession getTrainingSessionByCustomerName(String name);
    TrainingSession getTrainingSessionByStaffName(String name);
    TrainingSession addTrainingSession(TrainingSession TrainingSession);
}
