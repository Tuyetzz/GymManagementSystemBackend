package com.example.gympool.repository;

import com.example.gympool.entity.ClassTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassTemplateRepository extends JpaRepository<ClassTemplate, Long> {
    Optional<ClassTemplate> findByName(String name);
}
