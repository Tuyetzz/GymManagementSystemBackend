package com.example.gympool.repository;

import com.example.gympool.entity.MembershipPlan;
import com.example.gympool.entity.MembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
}
