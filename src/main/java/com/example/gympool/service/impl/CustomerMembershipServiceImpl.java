package com.example.gympool.service.impl;

import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.entity.Member;

import com.example.gympool.entity.MembershipTier;
import com.example.gympool.repository.CustomerMembershipRepository;
import com.example.gympool.repository.MembershipTierRepository;
import com.example.gympool.service.CustomerMembershipService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerMembershipServiceImpl implements CustomerMembershipService {
    private final CustomerMembershipRepository customerMembershipRepository;
    public CustomerMembershipServiceImpl(CustomerMembershipRepository customerMembershipRepository) {
        this.customerMembershipRepository = customerMembershipRepository;
    }
    @Override
    public List<CustomerMembership> getAllCustomerMembership(){
        return customerMembershipRepository.findAll();
    }
    @Override
    public CustomerMembership getMembershipById(Long id){
        return customerMembershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CustomerMembership not found with id: " + id));
    }
    @Override
    public CustomerMembership getMembershipByCustomerName(String customerName){
        return customerMembershipRepository.findByMemberName(customerName)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with name: " + customerName));
    }
    @Override
    public CustomerMembership RegisterMembership(CustomerMembership customerMembership){
        return customerMembershipRepository.save(customerMembership);
    }
    @Override
    public CustomerMembership updateMembership(Long id, CustomerMembership customerMembershipDetails){
        CustomerMembership customerMembership = getMembershipById(id);

        // update field từ User
        if (customerMembershipDetails.getMembershipPlan() != null) customerMembership.setMembershipPlan(customerMembershipDetails.getMembershipPlan());
        if (customerMembershipDetails.getStatus() != null) customerMembership.setStatus(customerMembershipDetails.getStatus());
        if (customerMembershipDetails.getMember() != null) customerMembership.setMember(customerMembershipDetails.getMember());
        if (customerMembershipDetails.getStartDate() != null) customerMembership.setStartDate(customerMembershipDetails.getStartDate());
        if (customerMembershipDetails.getEndDate() != null) customerMembership.setEndDate(customerMembershipDetails.getEndDate());
        return customerMembershipRepository.save(customerMembership);
    }
}
