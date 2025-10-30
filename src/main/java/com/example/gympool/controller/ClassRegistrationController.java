package com.example.gympool.controller;

import com.example.gympool.entity.ClassRegistration;
import com.example.gympool.service.ClassRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-registrations")
public class ClassRegistrationController {

    private final ClassRegistrationService classRegistrationService;

    public ClassRegistrationController(ClassRegistrationService classRegistrationService) {
        this.classRegistrationService = classRegistrationService;
    }

    // Lấy danh sách lớp mà staff đã đăng ký dạy
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<ClassRegistration>> getByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(classRegistrationService.getByStaff(staffId));
    }

    // Lấy danh sách giáo viên đã đăng ký cho class template
    @GetMapping("/classtemplate/{templateId}")
    public ResponseEntity<List<ClassRegistration>> getByClassTemplate(@PathVariable Long templateId) {
        return ResponseEntity.ok(classRegistrationService.getByClassTemplate(templateId));
    }

    // Staff đăng ký dạy 1 lớp
    @PostMapping
    public ResponseEntity<ClassRegistration> registerTeaching(@RequestBody ClassRegistration reg) {
        return ResponseEntity.ok(classRegistrationService.registerTeaching(reg));
    }

    // Staff hủy đăng ký dạy
    @DeleteMapping("/{id}")
    public ResponseEntity<String> unregisterTeaching(@PathVariable Long id) { // Dùng @PathVariable
        classRegistrationService.unregisterTeachingById(id); // Gọi một service mới theo ID
        return ResponseEntity.ok("Teaching registration removed successfully");
    }
}
