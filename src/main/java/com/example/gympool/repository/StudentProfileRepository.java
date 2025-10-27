package com.example.gympool.repository;

import com.example.gympool.entity.MembershipPlan;
import com.example.gympool.entity.PTPackageIssued;
import com.example.gympool.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    @Query("SELECT sf FROM StudentProfile sf WHERE sf.member.fullName LIKE %:name%")
    Optional<StudentProfile> findByMemberName(String name);

    @Query("SELECT sf FROM StudentProfile sf WHERE sf.staff.fullName LIKE %:name%")
    Optional<StudentProfile> findByStaffName(String name);
}
