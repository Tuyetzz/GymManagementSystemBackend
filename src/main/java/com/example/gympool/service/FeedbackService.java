package com.example.gympool.service;

import com.example.gympool.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback create(Feedback feedback);
    void delete(Feedback feedback);
    Feedback findById(Long id);
    List<Feedback> findAll();
}
