package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassTemplate;
import com.example.gympool.repository.ClassTemplateRepository;
import com.example.gympool.service.ClassTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassTemplateServiceImpl implements ClassTemplateService {

    private final ClassTemplateRepository classTemplateRepository;

    public ClassTemplateServiceImpl(ClassTemplateRepository classTemplateRepository) {
        this.classTemplateRepository = classTemplateRepository;
    }

    @Override
    public List<ClassTemplate> getAll() {
        return classTemplateRepository.findAll();
    }

    @Override
    public Optional<ClassTemplate> getById(Long id) {
        return classTemplateRepository.findById(id);
    }

    @Override
    public ClassTemplate create(ClassTemplate classTemplate) {
        return classTemplateRepository.save(classTemplate);
    }

    @Override
    public ClassTemplate update(Long id, ClassTemplate classTemplate) {
        return classTemplateRepository.findById(id)
                .map(existing -> {
                    existing.setName(classTemplate.getName());
                    existing.setDescription(classTemplate.getDescription());
                    existing.setDifficultyLevel(classTemplate.getDifficultyLevel());
                    existing.setStatus(classTemplate.getStatus());
                    return classTemplateRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ClassType not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        if (!classTemplateRepository.existsById(id)) {
            throw new RuntimeException("ClassType not found with id " + id);
        }
        classTemplateRepository.deleteById(id);
    }
}
