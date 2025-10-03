package com.example.gympool.repository;

import com.example.gympool.entity.ClassTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTemplateRepository extends JpaRepository<ClassTemplate, Long> {
    ClassTemplate findByName(String name);
}
