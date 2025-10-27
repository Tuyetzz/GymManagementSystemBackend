package com.example.gympool.service;

import com.example.gympool.entity.MembershipPlan;

import java.util.List;

public interface MembershipPlanService {
    List<MembershipPlan> getAllMembershipPlan();
    MembershipPlan getMembershipPlanById(Long id);
    MembershipPlan addMembershipPlan(MembershipPlan MembershipPlan);
    MembershipPlan updateMembershipPlan(Long id, MembershipPlan membershipPlan);
}
