package com.example.gympool.repository;

import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.PTPackageIssued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PTPackageIssuedRepository extends JpaRepository<PTPackageIssued, Long> {
    @Query("SELECT pi FROM PTPackageIssued pi WHERE pi.member.fullName LIKE %:name%")
    Optional<PTPackageIssued> findByMemberName(String name);

    @Query("SELECT pi FROM PTPackageIssued pi WHERE pi.staff.fullName LIKE %:name%")
    Optional<PTPackageIssued> findByStaffName(String name);
}
