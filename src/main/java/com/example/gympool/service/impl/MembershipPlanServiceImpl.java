package com.example.gympool.service.impl;

import com.example.gympool.entity.MembershipPlan;
import com.example.gympool.repository.MembershipPlanRepository;
import com.example.gympool.service.MembershipPlanService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MembershipPlanServiceImpl implements MembershipPlanService {
    private final MembershipPlanRepository MembershipPlanRepository;
    public MembershipPlanServiceImpl(MembershipPlanRepository MembershipPlanRepository) {
        this.MembershipPlanRepository = MembershipPlanRepository;
    }
    @Override
    public List<MembershipPlan> getAllMembershipPlan(){
        return MembershipPlanRepository.findAll();
    }
    @Override
    public MembershipPlan getMembershipPlanById(Long id){
        return MembershipPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MembershipPlan not found with id: " + id));
    }
    @Override
    public MembershipPlan addMembershipPlan(MembershipPlan MembershipPlan) {
        return MembershipPlanRepository.save(MembershipPlan);
    }

    @Override
    public MembershipPlan updateMembershipPlan(Long id, MembershipPlan MembershipPlanDetails) {
        MembershipPlan MembershipPlan = getMembershipPlanById(id);

        // update field từ User
        if (MembershipPlanDetails.getName() != null) MembershipPlan.setName(MembershipPlanDetails.getName());
        if (MembershipPlanDetails.getDuration() != null) MembershipPlan.setDuration(MembershipPlanDetails.getDuration());
        if (MembershipPlanDetails.getStatus() != null) MembershipPlan.setStatus(MembershipPlanDetails.getStatus());
        if (MembershipPlanDetails.getBenefits() != null) MembershipPlan.setBenefits(MembershipPlanDetails.getBenefits());
        if (MembershipPlanDetails.getPrice() != null) MembershipPlan.setPrice(MembershipPlanDetails.getPrice());
        return MembershipPlanRepository.save(MembershipPlan);
    }

}
