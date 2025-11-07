package com.example.gympool.repository;

import com.example.gympool.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    @Modifying
    @Query("UPDATE Product p SET p.status = true WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Product p SET p.status = false WHERE p.id = :id")
    void restoreById(@Param("id") Long id);

    List<Product> findAllByStatusFalse();   //lay chua bi xoa mem

    Optional<Product> findByIdAndStatusFalse(Long id);  //lay 1 cai chua xoa mem
}