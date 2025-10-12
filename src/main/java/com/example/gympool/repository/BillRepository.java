package com.example.gympool.repository;

import com.example.gympool.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByReceptionist_Id(Long managerId);
    List<Bill> findByMember_id(Long memberId);
}
