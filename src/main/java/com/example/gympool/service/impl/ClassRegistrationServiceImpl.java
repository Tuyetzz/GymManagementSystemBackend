package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassRegistration;
import com.example.gympool.entity.ClassTemplate;
import com.example.gympool.entity.Staff;
import com.example.gympool.repository.ClassRegistrationRepository;
import com.example.gympool.repository.ClassTemplateRepository;
import com.example.gympool.repository.StaffRepository;
import com.example.gympool.service.ClassRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRegistrationServiceImpl implements ClassRegistrationService {

    @Autowired
    private ClassRegistrationRepository classRegistrationRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ClassTemplateRepository classTemplateRepository;

    @Override
    public List<ClassRegistration> getByStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));
        return classRegistrationRepository.findByStaff(staff);
    }

    @Override
    public List<ClassRegistration> getByClassTemplate(Long templateId) {
        ClassTemplate template = classTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("ClassTemplate not found with id: " + templateId));
        return classRegistrationRepository.findByClassTemplate(template);
    }

    @Override
    public ClassRegistration registerTeaching(ClassRegistration reg) {
        // Lấy entity từ DB
        Staff staff = staffRepository.findById(reg.getStaff().getId())
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + reg.getStaff().getId()));
        ClassTemplate template = classTemplateRepository.findById(reg.getClassTemplate().getId())
                .orElseThrow(() -> new RuntimeException("ClassTemplate not found with id: " + reg.getClassTemplate().getId()));

        // Kiểm tra lớp đã có người đăng ký chưa
        List<ClassRegistration> existing = classRegistrationRepository.findByClassTemplate(template);
        if (!existing.isEmpty()) {
            throw new RuntimeException("This class template already has a teacher registered.");
        }

        // Gắn lại entity từ DB để tránh lỗi transient
        reg.setStaff(staff);
        reg.setClassTemplate(template);

        return classRegistrationRepository.save(reg);
    }

    @Override
    public void unregisterTeaching(ClassRegistration reg) {
        Long staffId = reg.getStaff().getId();
        Long templateId = reg.getClassTemplate().getId();

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        classRegistrationRepository.findByStaff(staff).stream()
                .filter(r -> r.getClassTemplate().getId().equals(templateId))
                .findFirst()
                .ifPresentOrElse(
                        classRegistrationRepository::delete,
                        () -> { throw new RuntimeException("Registration not found for this class template."); }
                );
    }
}
