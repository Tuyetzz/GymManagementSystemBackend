package com.example.gympool.service;

import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.TeachingRegistration;

import java.util.List;
import java.util.Optional;

public interface ClassSlotService {
    List<ClassSlot> getAll();
    Optional<ClassSlot> getById(Long id);
    ClassSlot create(ClassSlot classSlot);
    ClassSlot update(Long id, ClassSlot classSlot);
    void delete(Long id);





}
