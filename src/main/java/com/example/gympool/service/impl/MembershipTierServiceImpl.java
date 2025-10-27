package com.example.gympool.service.impl;

import com.example.gympool.entity.Member;
import com.example.gympool.entity.MembershipTier;
import com.example.gympool.repository.MembershipTierRepository;
import com.example.gympool.service.MembershipTierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class MembershipTierServiceImpl implements MembershipTierService {
    private final MembershipTierRepository membershipTierRepository;

    @Override
    public List<MembershipTier> getAllMembershipTier(){
        return membershipTierRepository.findAll();
    }
    @Override
    public MembershipTier getMembershipTierById(Long id){
        return membershipTierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MembershipTier not found with id: " + id));
    }
    @Override
    public MembershipTier addMembershipTier(MembershipTier membershipTier) {
        return membershipTierRepository.save(membershipTier);
    }

    @Override
    public MembershipTier updateMembershipTier(Long id, MembershipTier membershipTierDetails) {
        MembershipTier membershipTier = getMembershipTierById(id);

        if (membershipTierDetails.getName() != null) membershipTier.setName(membershipTierDetails.getName());
        if (membershipTierDetails.getPriority() != null) membershipTier.setPriority(membershipTierDetails.getPriority());
        if (membershipTierDetails.getStatus() != null) membershipTier.setStatus(membershipTierDetails.getStatus());
        return membershipTierRepository.save(membershipTier);
    }

}
