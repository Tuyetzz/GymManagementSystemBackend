package com.example.gympool.repository;

import com.example.gympool.entity.ImportedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportedProductRepository extends JpaRepository<ImportedProduct, Long> {
}
