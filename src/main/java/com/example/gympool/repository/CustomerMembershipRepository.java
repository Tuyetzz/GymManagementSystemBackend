package com.example.gympool.repository;

import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.entity.Member;
import com.example.gympool.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerMembershipRepository extends JpaRepository<CustomerMembership, Long> {
    @Query("SELECT cm FROM CustomerMembership cm WHERE cm.member.fullName LIKE %:name%")
    Optional<CustomerMembership> findByMemberName(String name);
}
