package com.example.gympool.controller;

import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.entity.CustomerMembership;
import com.example.gympool.service.CustomerMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership")
@RequiredArgsConstructor
public class CustomerMembershipController {
    private final CustomerMembershipService customerMembershipService;
    @GetMapping()
    public List<CustomerMembership> findAll() {
        return customerMembershipService.getAllCustomerMembership();
    }
    @GetMapping("/{id}")
    public CustomerMembership findCustomerMembershipById(@PathVariable("id") Long id) {
        return customerMembershipService.getMembershipById(id);
    }
    @GetMapping("/")
    public CustomerMembership findCustomerMembershipByName(@RequestParam("name") String name) {
        return customerMembershipService.getMembershipByCustomerName(name);
    }
    @PostMapping()
    public void RegisterMembership(@RequestBody CustomerMembership CustomerMembership) {
        customerMembershipService.RegisterMembership(CustomerMembership);
    }

    @PutMapping("/{id}")
    public void updateCustomerMembership(@PathVariable("id") Long id,
                                     @RequestBody CustomerMembership CustomerMembership) {
        customerMembershipService.updateMembership(id, CustomerMembership);
    }
    }


