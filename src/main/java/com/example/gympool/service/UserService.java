package com.example.gympool.service;

import com.example.gympool.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
