package com.example.gympool.service.impl;

import com.example.gympool.entity.User;
import com.example.gympool.repository.UserRepository;
import com.example.gympool.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // ==================== LẤY DỮ LIỆU ====================

    @Override
    public List<User> getAllUsers() {
        // Chỉ lấy user chưa bị xoá
        return userRepository.findAll()
                .stream()
                .filter(u -> !u.isDeleted())
                .toList();
    }

    @Override
    public List<User> getAllStaffs() {
        return userRepository.findAllStaffs();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    // ==================== THÊM / CẬP NHẬT ====================

    @Override
    public void addUser(User user) {
        user.setDeleted(false); // đảm bảo không bị xoá mềm
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // Đảm bảo user tồn tại và chưa bị xoá
        User existing = getUserById(user.getId());
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setGender(user.getGender());
        existing.setDob(user.getDob());
        existing.setRole(user.getRole());
        existing.setPassword(user.getPassword());
        userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {

    }

    // ==================== XOÁ MỀM / KHÔI PHỤC ====================

    @Override
    public void softDeleteUser(Long id) {
        userRepository.softDeleteById(id);
    }

    @Override
    public void restoreUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setDeleted(false);
        userRepository.save(user);
    }

    @Override
    public List<User> getDeletedUsers() {
        return userRepository.findAll()
                .stream()
                .filter(User::isDeleted)
                .toList();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findAllByRoleAndIsDeletedFalse(role);
    }
}
