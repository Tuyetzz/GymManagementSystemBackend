package com.example.gympool.repository;

import com.example.gympool.entity.StaffAssigned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffAssignedRepository extends JpaRepository<StaffAssigned, Long> {
    List<StaffAssigned> findByStaff_Id(Long staffId);
    List<StaffAssigned> findByBill_Id(Long billId);
}
