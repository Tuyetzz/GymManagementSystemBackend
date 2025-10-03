package com.example.gympool.service;

import com.example.gympool.entity.ClassTemplate;

import java.util.List;
import java.util.Optional;

public interface ClassTemplateService {
    List<ClassTemplate> getAll();
    Optional<ClassTemplate> getById(Long id);
    ClassTemplate create(ClassTemplate classTemplate);
    ClassTemplate update(Long id, ClassTemplate classTemplate);
    void delete(Long id);
}
