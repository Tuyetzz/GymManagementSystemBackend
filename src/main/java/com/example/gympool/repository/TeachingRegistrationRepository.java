package com.example.gympool.repository;

import com.example.gympool.entity.TeachingRegistration;
import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingRegistrationRepository extends JpaRepository<TeachingRegistration, Long> {
    List<TeachingRegistration> findByStaff(Staff staff);
    List<TeachingRegistration> findByClassSlot(ClassSlot classSlot);
}
