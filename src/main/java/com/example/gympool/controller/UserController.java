package com.example.gympool.controller;

import com.example.gympool.entity.User;
import com.example.gympool.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==================== LẤY DỮ LIỆU ====================

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<User>> getAllStaffs() {
        return ResponseEntity.ok(userService.getAllStaffs());
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<User>> getDeletedUsers() {
        return ResponseEntity.ok(userService.getDeletedUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role.toUpperCase()));
    }

    // ==================== THÊM / CẬP NHẬT ====================

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }

    // ==================== XOÁ MỀM / KHÔI PHỤC ====================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok("User soft deleted successfully");
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<String> restoreUser(@PathVariable Long id) {
        userService.restoreUser(id);
        return ResponseEntity.ok("User restored successfully");
    }
}
