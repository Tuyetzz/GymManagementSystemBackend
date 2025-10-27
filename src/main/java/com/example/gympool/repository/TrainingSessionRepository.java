package com.example.gympool.repository;

import com.example.gympool.entity.StudentProfile;
import com.example.gympool.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    @Query("SELECT ts FROM TrainingSession ts WHERE ts.ptAppointment.member.fullName LIKE %:name%")
    Optional<TrainingSession> findByMemberName(String name);

    @Query("SELECT ts FROM TrainingSession ts WHERE ts.ptAppointment.staff.fullName LIKE %:name%")
    Optional<TrainingSession> findByStaffName(String name);
}
