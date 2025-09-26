package com.example.gympool.service;

import com.example.gympool.entity.ClassType;

import java.util.List;
import java.util.Optional;

public interface ClassTypeService {
    List<ClassType> getAll();
    Optional<ClassType> getById(Long id);
    ClassType create(ClassType classType);
    ClassType update(Long id, ClassType classType);
    void delete(Long id);
}
