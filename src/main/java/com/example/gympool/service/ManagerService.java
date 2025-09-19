package com.example.gympool.service;

import com.example.gympool.entity.Manager;

import java.util.List;

public interface ManagerService {
    List<Manager> getAllManagers();
    Manager getManagerById(Long id);
    Manager createManager(Manager manager);
    Manager updateManager(Long id, Manager manager);
    void deleteManager(Long id);
}
