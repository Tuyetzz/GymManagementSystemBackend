package com.example.gympool.controller;

import com.example.gympool.entity.ClassRegistration;
import com.example.gympool.service.ClassRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-registrations")
public class ClassRegistrationController {

    private final ClassRegistrationService teachingRegistrationService;

    public ClassRegistrationController(ClassRegistrationService teachingRegistrationService) {
        this.teachingRegistrationService = teachingRegistrationService;
    }

    // Lấy danh sách lớp mà staff đã đăng ký
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<ClassRegistration>> getByStaff(@PathVariable Long staffId) {
        return ResponseEntity.ok(teachingRegistrationService.getByStaff(staffId));
    }

    // Lấy danh sách giáo viên đã đăng ký cho class slot
    @GetMapping("/classschedule/{scheduleId}")
    public ResponseEntity<List<ClassRegistration>> getByClassSlot(@PathVariable Long slotId) {
        return ResponseEntity.ok(teachingRegistrationService.getByClassSlot(slotId));
    }

    // Staff đăng ký dạy 1 lớp
    @PostMapping
    public ResponseEntity<ClassRegistration> registerTeaching(
            @RequestBody ClassRegistration reg) {
        return ResponseEntity.ok(teachingRegistrationService.registerTeaching(reg));
    }


    // Staff hủy đăng ký dạy
    @DeleteMapping
    public ResponseEntity<String> unregisterTeaching(@RequestBody ClassRegistration reg) {
        teachingRegistrationService.unregisterTeaching(reg);
        return ResponseEntity.ok("Teaching registration removed successfully");
    }
}
