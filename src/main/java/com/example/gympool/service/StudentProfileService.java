package com.example.gympool.service;


import com.example.gympool.entity.StudentProfile;

import java.util.List;

public interface StudentProfileService {
    List<StudentProfile> getAllStudentProfile();
    StudentProfile getStudentProfileById(Long id);
    StudentProfile getStudentProfileByCustomerName(String name);
    StudentProfile getStudentProfileByStaffName(String name);
    StudentProfile addStudentProfile(StudentProfile StudentProfile);
    StudentProfile updateStudentProfile(Long id, StudentProfile StudentProfile);
}
