package com.example.gympool.service.impl;

import com.example.gympool.entity.TrainingSession;
import com.example.gympool.entity.TrainingSession;
import com.example.gympool.repository.TrainingSessionRepository;
import com.example.gympool.service.TrainingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingSessionServiceImpl implements TrainingSessionService {
    private final TrainingSessionRepository trainingSessionRepository;
    @Override
    public List<TrainingSession> getAllTrainingSession(){
        return trainingSessionRepository.findAll();
    }
    @Override
    public TrainingSession getTrainingSessionById(Long id){
        return trainingSessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TrainingSession not found with id: " + id));
    }
    @Override
    public TrainingSession addTrainingSession(TrainingSession trainingSession) {
        return trainingSessionRepository.save(trainingSession);
    }
    @Override
    public TrainingSession getTrainingSessionByCustomerName(String name){
        return trainingSessionRepository.findByMemberName(name)
                .orElseThrow(() -> new IllegalArgumentException("TrainingSession not found with MemberName: " + name));
    }
    @Override
    public TrainingSession getTrainingSessionByStaffName(String name){
        return trainingSessionRepository.findByStaffName(name)
                .orElseThrow(() -> new IllegalArgumentException("TrainingSession not found with StaffName: " + name));
    }
}
