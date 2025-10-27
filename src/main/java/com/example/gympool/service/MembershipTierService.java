package com.example.gympool.service;

import com.example.gympool.entity.MembershipTier;

import java.util.List;

public interface MembershipTierService {
    List <MembershipTier> getAllMembershipTier();
    MembershipTier getMembershipTierById(Long id);
    MembershipTier addMembershipTier(MembershipTier membershipTier);
    MembershipTier updateMembershipTier(Long id, MembershipTier membershipTier);

}
