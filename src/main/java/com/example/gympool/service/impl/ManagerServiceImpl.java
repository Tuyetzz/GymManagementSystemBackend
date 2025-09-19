package com.example.gympool.service.impl;

import com.example.gympool.entity.Manager;
import com.example.gympool.repository.ManagerRepository;
import com.example.gympool.service.ManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getManagerById(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found with id: " + id));
    }

    @Override
    public Manager createManager(Manager manager) {
        manager.setRole("MANAGER"); // ép role cho chắc chắn
        return managerRepository.save(manager);
    }

    @Override
    public Manager updateManager(Long id, Manager managerDetails) {
        Manager manager = getManagerById(id);
        manager.setEmail(managerDetails.getEmail());
        manager.setPassword(managerDetails.getPassword());
        manager.setFullName(managerDetails.getFullName());
        manager.setDob(managerDetails.getDob());
        manager.setGender(managerDetails.getGender());
        manager.setPhone(managerDetails.getPhone());
        return managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
