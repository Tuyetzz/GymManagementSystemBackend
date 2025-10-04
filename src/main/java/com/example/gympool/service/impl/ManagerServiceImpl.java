package com.example.gympool.service.impl;

import com.example.gympool.entity.Manager;
import com.example.gympool.repository.ManagerRepository;
import com.example.gympool.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }

    @Override
    public Manager updateManager(Long id, Manager managerUpdate) {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            manager.setEmail(managerUpdate.getEmail());
            manager.setPassword(managerUpdate.getPassword());
            manager.setFullName(managerUpdate.getFullName());
            manager.setDob(managerUpdate.getDob());
            manager.setGender(managerUpdate.getGender());
            manager.setPhone(managerUpdate.getPhone());
            manager.setRole(managerUpdate.getRole());
            return managerRepository.save(manager);
        }
        return null;
    }

    @Override
    public Optional<Manager> findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }
}
