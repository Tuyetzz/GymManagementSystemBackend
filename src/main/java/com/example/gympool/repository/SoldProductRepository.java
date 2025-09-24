package com.example.gympool.repository;

import com.example.gympool.entity.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
    List<SoldProduct> findByBill_Id(Long billId);
    List<SoldProduct> findByProduct_Id(Long productId);
}
