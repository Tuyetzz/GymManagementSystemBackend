package com.example.gympool.repository;

import com.example.gympool.entity.ImportedProduct;
import com.example.gympool.entity.MembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipTierRepository extends JpaRepository<MembershipTier, Long> {
}
