package com.example.gympool.service;

import com.example.gympool.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    List<User> getAllStaffs();
    User getUserByEmail(String email);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    void softDeleteUser(Long id);
    void restoreUser(Long id);
    List<User> getDeletedUsers();
    List<User> getUsersByRole(String role);
}
