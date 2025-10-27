package com.example.gympool.service;

import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.entity.MembershipPlan;

import java.util.List;

public interface CustomerMembershipService {
    List<CustomerMembership> getAllCustomerMembership();
    CustomerMembership getMembershipById(Long id);
    CustomerMembership getMembershipByCustomerName(String customerName);
    CustomerMembership RegisterMembership(CustomerMembership customerMembership);
    CustomerMembership updateMembership(Long id, CustomerMembership customerMembership);
}
