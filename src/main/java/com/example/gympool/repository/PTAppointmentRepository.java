package com.example.gympool.repository;

import com.example.gympool.entity.PTAppointment;
import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PTAppointmentRepository extends JpaRepository<PTAppointment, Long> {
    @Query("SELECT pa FROM PTAppointment pa WHERE pa.ptPackageIssued.member.fullName LIKE %:name%")
    Optional<PTAppointment> findByMemberName(String name);

    @Query("SELECT pa FROM PTAppointment pa WHERE pa.staff.fullName LIKE %:name%")
    Optional<PTAppointment> findByStaffName(String name);

    @Query("SELECT pa FROM PTAppointment pa WHERE (pa.startTime BETWEEN :start and :end) and pa.notificationSent =false")
    List<PTAppointment> findUpcomingAppointments(LocalDateTime start, LocalDateTime end);
}
