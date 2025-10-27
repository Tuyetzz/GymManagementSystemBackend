package com.example.gympool.service;

import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.PTPackageIssued;

import java.util.List;

public interface PTPackageIssuedService {
    List<PTPackageIssued> getAllPTPackageIssued();
    PTPackageIssued getPTPackageIssuedById(Long id);
    PTPackageIssued getPTPackageIssuedByCustomerName(String name);
    PTPackageIssued getPTPackageIssuedByStaffName(String name);
    PTPackageIssued registerPTPackage(PTPackageIssued ptPackageIssued);
    PTPackageIssued updatePTPackageIssued(Long id, PTPackageIssued ptPackageIssued);
}
