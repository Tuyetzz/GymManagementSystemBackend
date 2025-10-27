package com.example.gympool.service.impl;

import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.PTPackageIssued;
import com.example.gympool.repository.PTPackageIssuedRepository;
import com.example.gympool.repository.PTPackageRepository;
import com.example.gympool.service.PTPackageIssuedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PTPackageIssuedServiceImpl implements PTPackageIssuedService {
    private final PTPackageIssuedRepository ptPackageIssuedRepository;
    @Override
    public List<PTPackageIssued> getAllPTPackageIssued(){
        return ptPackageIssuedRepository.findAll();
    }
    @Override
    public PTPackageIssued getPTPackageIssuedById(Long id){
        return ptPackageIssuedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PTPackageIssued not found with id: " + id));
    }
    @Override
    public PTPackageIssued registerPTPackage(PTPackageIssued ptPackage) {
        return ptPackageIssuedRepository.save(ptPackage);
    }

    @Override
    public PTPackageIssued updatePTPackageIssued(Long id, PTPackageIssued ptPackageUpd) {
        PTPackageIssued ptPackage = getPTPackageIssuedById(id);

        if (ptPackageUpd.getRemainingSessions() != null) ptPackage.setRemainingSessions(ptPackageUpd.getRemainingSessions());
        if (ptPackageUpd.getStaff() != null) ptPackage.setStaff(ptPackageUpd.getStaff());
        return ptPackageIssuedRepository.save(ptPackage);
    }
    @Override
    public PTPackageIssued getPTPackageIssuedByCustomerName(String name){
        return ptPackageIssuedRepository.findByMemberName(name)
                .orElseThrow(() -> new IllegalArgumentException("PTPackageIssued not found with MemberName: " + name));
    }
    @Override
    public PTPackageIssued getPTPackageIssuedByStaffName(String name){
        return ptPackageIssuedRepository.findByStaffName(name)
                .orElseThrow(() -> new IllegalArgumentException("PTPackageIssued not found with StaffName: " + name));
    }
}
