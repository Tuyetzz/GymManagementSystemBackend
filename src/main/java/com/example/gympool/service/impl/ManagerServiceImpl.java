package com.example.gympool.service.impl;

import com.example.gympool.entity.Manager;
import com.example.gympool.repository.ManagerRepository;
import com.example.gympool.service.ManagerService;
import org.springframework.stereotype.Service;
import java.util.Optional;
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
                .orElseThrow(() -> new IllegalArgumentException("Manager not found with id: " + id));
    }

    @Override
    public Optional<Manager> getManagerByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    @Override
    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager updateManager(Long id, Manager managerDetails) {
        Manager manager = getManagerById(id);

        // cập nhật field chung từ User
        if (managerDetails.getEmail() != null) manager.setEmail(managerDetails.getEmail());
        if (managerDetails.getPassword() != null) manager.setPassword(managerDetails.getPassword());
        if (managerDetails.getFullName() != null) manager.setFullName(managerDetails.getFullName());
        if (managerDetails.getDob() != null) manager.setDob(managerDetails.getDob());
        if (managerDetails.getGender() != null) manager.setGender(managerDetails.getGender());
        if (managerDetails.getPhone() != null) manager.setPhone(managerDetails.getPhone());

        return managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Long id) {
        if (!managerRepository.existsById(id)) {
            throw new IllegalArgumentException("Manager not found with id: " + id);
        }
        managerRepository.deleteById(id);
    }
}
