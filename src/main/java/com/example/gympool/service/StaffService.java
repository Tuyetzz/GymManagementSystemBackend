package com.example.gympool.service;

import com.example.gympool.entity.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffService {
    Staff createStaff(Staff staff);
    Staff updateStaff(Long id, Staff staff);
    void deleteStaff(Long id);
    Staff getStaffById(Long id);
    Optional<Staff> getStaffByEmail(String email);
    List<Staff> getAllStaffs();
}
