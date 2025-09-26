package com.example.gympool.service;

import com.example.gympool.entity.TeachingRegistration;
import java.util.List;

public interface TeachingRegistrationService {
    List<TeachingRegistration> getByStaff(Long staffId);
    List<TeachingRegistration> getByClassSlot(Long slotId);
    // Staff đăng ký dạy 1 lớp
    TeachingRegistration registerTeaching(TeachingRegistration reg);

    // Staff hủy đăng ký dạy
    void unregisterTeaching(TeachingRegistration reg);

}
