package com.example.gympool.controller;

import com.example.gympool.entity.StudentProfile;
import com.example.gympool.entity.StudentProfile;
import com.example.gympool.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentprofile")
@RequiredArgsConstructor
public class StudentProfileController {
    private final StudentProfileService studentProfileService;
    
    @GetMapping()
    public List<StudentProfile> findAll() {
        return studentProfileService.getAllStudentProfile();
    }
    @GetMapping("/{id}")
    public StudentProfile findStudentProfileById(@PathVariable("id") Long id) {
        return studentProfileService.getStudentProfileById(id);
    }
    @GetMapping("/{member}")
    public StudentProfile findStudentProfileByCustomerName(@RequestParam("name") String name) {
        return studentProfileService.getStudentProfileByCustomerName(name);
    }
    @GetMapping("/{staff}")
    public StudentProfile findStudentProfileByStaffName(@RequestParam("name") String name) {
        return studentProfileService.getStudentProfileByStaffName(name);
    }
    @PostMapping()
    public void addStudentProfile(@RequestBody StudentProfile studentProfile) {
        studentProfileService.addStudentProfile(studentProfile);
    }

    @PutMapping("/{id}")
    public void updateStudentProfile(@PathVariable("id") Long id,
                                     @RequestBody StudentProfile studentProfile) {
        studentProfileService.updateStudentProfile(id, studentProfile);
    }
}
