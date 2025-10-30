package com.example.gympool.controller;

import com.example.gympool.entity.MembershipPlan;
import com.example.gympool.entity.MembershipPlan;
import com.example.gympool.service.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membershipplan")
@RequiredArgsConstructor
public class MembershipPlanController {
    private final MembershipPlanService membershipPlanService;
    @GetMapping()
    public List<MembershipPlan> findAll() {
        return membershipPlanService.getAllMembershipPlan();
    }
    @GetMapping("/{id}")
    public MembershipPlan findMembershipPlanById(@PathVariable("id") Long id) {
        return membershipPlanService.getMembershipPlanById(id);
    }
    @PostMapping()
    public void addMembershipPlan(@RequestBody MembershipPlan MembershipPlan) {
        membershipPlanService.addMembershipPlan(MembershipPlan);
    }

    @PutMapping("/{id}")
    public void updateMembershipPlan(@PathVariable("id") Long id,
                                     @RequestBody MembershipPlan MembershipPlan) {
        membershipPlanService.updateMembershipPlan(id, MembershipPlan);
    }
}
