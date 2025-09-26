package com.example.gympool.controller;

import com.example.gympool.entity.TeachingRegistration;
import com.example.gympool.service.TeachingRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//{{baseUrl}}/api/teaching-registrations
@RestController
@RequestMapping("/api/teaching-registrations")
public class TeachingRegistrationController {

    private final TeachingRegistrationService teachingRegistrationService;

    public TeachingRegistrationController(TeachingRegistrationService teachingRegistrationService) {
        this.teachingRegistrationService = teachingRegistrationService;
    }

    // Lấy danh sách lớp mà staff đã đăng ký
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<TeachingRegistration>> getByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(teachingRegistrationService.getByStaff(staffId));
    }

    // Lấy danh sách giáo viên đã đăng ký cho class slot
    @GetMapping("/classslot/{slotId}")
    public ResponseEntity<List<TeachingRegistration>> getByClassSlot(@PathVariable Long slotId) {
        return ResponseEntity.ok(teachingRegistrationService.getByClassSlot(slotId));
    }

    // Staff đăng ký dạy 1 lớp
    @PostMapping
    public ResponseEntity<TeachingRegistration> registerTeaching(
            @RequestBody TeachingRegistration reg) {
        return ResponseEntity.ok(teachingRegistrationService.registerTeaching(reg));
    }


    // Staff hủy đăng ký dạy
    @DeleteMapping
    public ResponseEntity<String> unregisterTeaching(@RequestBody TeachingRegistration reg) {
        teachingRegistrationService.unregisterTeaching(reg);
        return ResponseEntity.ok("Teaching registration removed successfully");
    }
}
