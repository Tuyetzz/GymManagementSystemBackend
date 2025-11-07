package com.example.gympool.service.impl;

import com.example.gympool.dto.RegisterRequest;
import com.example.gympool.entity.Staff;
import com.example.gympool.entity.User;
import com.example.gympool.repository.StaffRepository;
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
    private StaffRepository staffRepository;
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
    public User updateUser(Long id, RegisterRequest request) { // <--- Đảm bảo trả về User
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ... (cập nhật các trường chung: fullName, email, dob...) ...
        existingUser.setFullName(request.getFullName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPhone(request.getPhone());
        existingUser.setGender(request.getGender());
        existingUser.setDob(request.getDob());
        existingUser.setRole(request.getRole());

        // Nếu user này là một Staff...
        if (existingUser instanceof Staff staff) {
            staff.setPosition(request.getPosition());
            staff.setSpecialize(request.getSpecialize());
            staff.setHirePrice(request.getHirePrice());

            // Lưu và TRẢ VỀ đối tượng Staff đã cập nhật
            return staffRepository.save(staff);
        }

        // ... (logic cho các role khác nếu cần) ...

        // Lưu và TRẢ VỀ đối tượng User đã cập nhật
        return userRepository.save(existingUser);
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
