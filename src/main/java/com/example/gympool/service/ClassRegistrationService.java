package com.example.gympool.service;

import com.example.gympool.entity.ClassRegistration;
import java.util.List;

public interface ClassRegistrationService {
    List<ClassRegistration> getByStaff(Long staffId);
    List<ClassRegistration> getByClassTemplate(Long templateId);
    // Staff đăng ký dạy 1 lớp
    ClassRegistration registerTeaching(ClassRegistration reg);

    // Staff hủy đăng ký dạy
    void unregisterTeaching(ClassRegistration reg);

}
