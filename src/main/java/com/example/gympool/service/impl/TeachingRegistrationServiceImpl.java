package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.Staff;
import com.example.gympool.entity.TeachingRegistration;
import com.example.gympool.repository.ClassSlotRepository;
import com.example.gympool.repository.StaffRepository;
import com.example.gympool.repository.TeachingRegistrationRepository;
import com.example.gympool.service.TeachingRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingRegistrationServiceImpl implements TeachingRegistrationService {

    private final TeachingRegistrationRepository teachingRegistrationRepository;
    private final StaffRepository staffRepository;
    private final ClassSlotRepository classSlotRepository;

    public TeachingRegistrationServiceImpl(TeachingRegistrationRepository teachingRegistrationRepository,
                                           StaffRepository staffRepository,
                                           ClassSlotRepository classSlotRepository) {
        this.teachingRegistrationRepository = teachingRegistrationRepository;
        this.staffRepository = staffRepository;
        this.classSlotRepository = classSlotRepository;
    }

    @Override
    public List<TeachingRegistration> getByStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return teachingRegistrationRepository.findByStaff(staff);
    }

    @Override
    public List<TeachingRegistration> getByClassSlot(Long slotId) {
        ClassSlot slot = classSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + slotId));
        return teachingRegistrationRepository.findByClassSlot(slot);
    }

    @Override
    public TeachingRegistration registerTeaching(TeachingRegistration reg) {
        //  slot
        ClassSlot slot = classSlotRepository.findById(reg.getClassSlot().getId())
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + reg.getClassSlot().getId()));
        //  staff
        Staff staff = staffRepository.findById(reg.getStaff().getId())
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + reg.getStaff().getId()));

        // check trùng
        if (!teachingRegistrationRepository.findByClassSlot(slot).isEmpty()) {
            throw new RuntimeException("This class already has a teacher registered");
        }

        // gắn lại entity từ DB (tránh lỗi transient object)
        reg.setClassSlot(slot);
        reg.setStaff(staff);

        return teachingRegistrationRepository.save(reg);
    }


    @Override
    public void unregisterTeaching(TeachingRegistration reg) {
        Long slotId = reg.getClassSlot().getId();
        Long staffId = reg.getStaff().getId();

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        teachingRegistrationRepository.findByStaff(staff).stream()
                .filter(tr -> tr.getClassSlot().getId().equals(slotId))
                .findFirst()
                .ifPresentOrElse(
                        teachingRegistrationRepository::delete,
                        () -> { throw new RuntimeException("TeachingRegistration not found"); }
                );
    }

}
