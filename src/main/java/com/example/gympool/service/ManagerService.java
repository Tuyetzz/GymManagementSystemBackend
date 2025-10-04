package com.example.gympool.service;

import com.example.gympool.entity.Manager;
import java.util.List;
import java.util.Optional;

public interface ManagerService {
    Manager saveManager(Manager manager);
    List<Manager> getAllManagers();
    Optional<Manager> getManagerById(Long id);
    void deleteManager(Long id);
    Manager updateManager(Long id, Manager manager);
    Optional<Manager> findByEmail(String email);
}
