package com.example.gympool.service.impl;

import com.example.gympool.entity.MembershipTier;
import com.example.gympool.entity.PTPackage;
import com.example.gympool.repository.PTPackageRepository;
import com.example.gympool.service.PTPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PTPackageServiceImpl implements PTPackageService {
    private final PTPackageRepository ptPackageRepository;
    @Override
    public  List<PTPackage> getAllPTPackage(){
        return ptPackageRepository.findAll();
    }
    @Override
    public PTPackage getPTPackageById(Long id){
        return ptPackageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PTPackage not found with id: " + id));
    }
    @Override
    public PTPackage addPTPackage(PTPackage ptPackage) {
        return ptPackageRepository.save(ptPackage);
    }

    @Override
    public PTPackage updatePTPackage(Long id, PTPackage ptPackageUpd) {
        PTPackage ptPackage = getPTPackageById(id);

        if (ptPackageUpd.getName() != null) ptPackage.setName(ptPackageUpd.getName());
        if (ptPackageUpd.getSessions() != null) ptPackage.setSessions(ptPackageUpd.getSessions());
        if (ptPackageUpd.getStatus() != null) ptPackage.setStatus(ptPackageUpd.getStatus());
        return ptPackageRepository.save(ptPackage);
    }

}
