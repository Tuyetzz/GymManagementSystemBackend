package com.example.gympool.service;

import com.example.gympool.entity.MembershipTier;
import com.example.gympool.entity.PTPackage;

import java.util.List;

public interface PTPackageService {
    List<PTPackage> getAllPTPackage();
    PTPackage getPTPackageById(Long id);
    PTPackage addPTPackage(PTPackage ptPackage);
    PTPackage updatePTPackage(Long id, PTPackage ptPackage);
}
