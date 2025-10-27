package com.example.gympool.controller;

import com.example.gympool.entity.MembershipTier;
import com.example.gympool.service.MembershipTierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membershiptier")
@RequiredArgsConstructor
public class MembershipTierController {
    private final MembershipTierService membershipTierService;

    @GetMapping()
    public List<MembershipTier> findAll() {
        return membershipTierService.getAllMembershipTier();
    }
    @GetMapping("/{id}")
    public MembershipTier findMembershipTierById(@PathVariable("id") Long id) {
        return membershipTierService.getMembershipTierById(id);
    }
    @PostMapping()
    public void addMembershipTier(@RequestBody MembershipTier membershipTier) {
         membershipTierService.addMembershipTier(membershipTier);
    }

    @PutMapping("/{id}")
    public void updateMembershipTier(@PathVariable("id") Long id,
                                     @RequestBody MembershipTier membershipTier) {
        membershipTierService.updateMembershipTier(id, membershipTier);
    }

}
