package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassType;
import com.example.gympool.repository.ClassTypeRepository;
import com.example.gympool.service.ClassTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassTypeServiceImpl implements ClassTypeService {

    private final ClassTypeRepository classTypeRepository;

    public ClassTypeServiceImpl(ClassTypeRepository classTypeRepository) {
        this.classTypeRepository = classTypeRepository;
    }

    @Override
    public List<ClassType> getAll() {
        return classTypeRepository.findAll();
    }

    @Override
    public Optional<ClassType> getById(Long id) {
        return classTypeRepository.findById(id);
    }

    @Override
    public ClassType create(ClassType classType) {
        return classTypeRepository.save(classType);
    }

    @Override
    public ClassType update(Long id, ClassType classType) {
        return classTypeRepository.findById(id)
                .map(existing -> {
                    existing.setName(classType.getName());
                    existing.setDescription(classType.getDescription());
                    existing.setDifficultyLevel(classType.getDifficultyLevel());
                    return classTypeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ClassType not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        if (!classTypeRepository.existsById(id)) {
            throw new RuntimeException("ClassType not found with id " + id);
        }
        classTypeRepository.deleteById(id);
    }
}
