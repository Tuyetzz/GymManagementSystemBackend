package com.example.gympool.repository;

import com.example.gympool.entity.ClassRegistration;
import com.example.gympool.entity.ClassTemplate;
import com.example.gympool.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Long> {
    List<ClassRegistration> findByStaff(Staff staff);
    List<ClassRegistration> findByClassTemplate(ClassTemplate classTemplate);
}
