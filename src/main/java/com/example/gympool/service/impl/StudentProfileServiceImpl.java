package com.example.gympool.service.impl;

import com.example.gympool.entity.StudentProfile;
import com.example.gympool.entity.StudentProfile;
import com.example.gympool.repository.StudentProfileRepository;
import com.example.gympool.repository.StudentProfileRepository;
import com.example.gympool.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {
    private final StudentProfileRepository studentProfileRepository;
    @Override
    public List<StudentProfile> getAllStudentProfile(){
        return studentProfileRepository.findAll();
    }
    @Override
    public StudentProfile getStudentProfileById(Long id){
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("StudentProfile not found with id: " + id));
    }
    @Override
    public StudentProfile addStudentProfile(StudentProfile ptPackage) {
        return studentProfileRepository.save(ptPackage);
    }

    @Override
    public StudentProfile updateStudentProfile(Long id, StudentProfile StudentProfileUpd) {
        StudentProfile studentProfile = getStudentProfileById(id);

        if (StudentProfileUpd.getHeight() != null) studentProfile.setHeight(StudentProfileUpd.getHeight());
        if (StudentProfileUpd.getWeight() != null) studentProfile.setWeight(StudentProfileUpd.getWeight());
        if (StudentProfileUpd.getStaff() != null) studentProfile.setStaff(StudentProfileUpd.getStaff());
        if (StudentProfileUpd.getTrainingPlan() != null) studentProfile.setTrainingPlan(StudentProfileUpd.getTrainingPlan());
        return studentProfileRepository.save(studentProfile);
    }
    @Override
    public StudentProfile getStudentProfileByCustomerName(String name){
        return studentProfileRepository.findByMemberName(name)
                .orElseThrow(() -> new IllegalArgumentException("StudentProfile not found with MemberName: " + name));
    }
    @Override
    public StudentProfile getStudentProfileByStaffName(String name){
        return studentProfileRepository.findByStaffName(name)
                .orElseThrow(() -> new IllegalArgumentException("StudentProfile not found with StaffName: " + name));
    }
}
