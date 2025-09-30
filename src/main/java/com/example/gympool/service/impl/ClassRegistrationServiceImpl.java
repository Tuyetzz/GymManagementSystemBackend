package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassSchedule;
import com.example.gympool.entity.Staff;
import com.example.gympool.entity.ClassRegistration;
import com.example.gympool.repository.ClassScheduleRepository;
import com.example.gympool.repository.StaffRepository;
import com.example.gympool.repository.ClassRegistrationRepository;
import com.example.gympool.service.ClassRegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRegistrationServiceImpl implements ClassRegistrationService {

    private final ClassRegistrationRepository classRegistrationRepository;
    private final StaffRepository staffRepository;
    private final ClassScheduleRepository classScheduleRepository;

    public ClassRegistrationServiceImpl(ClassRegistrationRepository classRegistrationRepository,
                                        StaffRepository staffRepository,
                                        ClassScheduleRepository classScheduleRepository) {
        this.classRegistrationRepository = classRegistrationRepository;
        this.staffRepository = staffRepository;
        this.classScheduleRepository = classScheduleRepository;
    }

    @Override
    public List<ClassRegistration> getByStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return classRegistrationRepository.findByStaff(staff);
    }

    @Override
    public List<ClassRegistration> getByClassSlot(Long slotId) {
        ClassSchedule slot = classScheduleRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + slotId));
        return classRegistrationRepository.findByClassSchedule(slot);
    }

    @Override
    public ClassRegistration registerTeaching(ClassRegistration reg) {
        //  slot
        ClassSchedule slot = classScheduleRepository.findById(reg.getClassSchedule().getId())
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + reg.getClassSchedule().getId()));
        //  staff
        Staff staff = staffRepository.findById(reg.getStaff().getId())
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + reg.getStaff().getId()));

        // check trùng
        if (!classRegistrationRepository.findByClassSchedule(slot).isEmpty()) {
            throw new RuntimeException("This class already has a teacher registered");
        }

        // gắn lại entity từ DB (tránh lỗi transient object)
        reg.setClassSchedule(slot);
        reg.setStaff(staff);

        return classRegistrationRepository.save(reg);
    }


    @Override
    public void unregisterTeaching(ClassRegistration reg) {
        Long slotId = reg.getClassSchedule().getId();
        Long staffId = reg.getStaff().getId();

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        classRegistrationRepository.findByStaff(staff).stream()
                .filter(tr -> tr.getClassSchedule().getId().equals(slotId))
                .findFirst()
                .ifPresentOrElse(
                        classRegistrationRepository::delete,
                        () -> { throw new RuntimeException("TeachingRegistration not found"); }
                );
    }

}
