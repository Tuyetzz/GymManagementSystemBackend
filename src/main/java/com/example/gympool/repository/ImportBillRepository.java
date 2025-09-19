package com.example.gympool.repository;

import com.example.gympool.entity.ImportBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportBillRepository extends JpaRepository<ImportBill, Long> {
    // có thể thêm query custom sau này
}
