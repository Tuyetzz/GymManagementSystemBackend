package com.example.gympool.repository;

import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassSlotRepository extends JpaRepository<ClassSlot, Long> {
    List<ClassSlot> findByClassType(ClassType classType);
    List<ClassSlot> findByStatus(String status);
    List<ClassSlot> findByStartTimeBetween(Date start, Date end);
}
