package com.example.gympool.service.impl;

import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.service.ClassSlotService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClassSlotServiceImpl implements ClassSlotService {

    private final ClassSlotRepository classSlotRepository;
    private final TeachingRegistrationRepository teachingRegistrationRepository;
    private final FollowClassRepository followClassRepository;
    private final StaffRepository staffRepository;
    private final MemberRepository memberRepository;

    public ClassSlotServiceImpl(ClassSlotRepository classSlotRepository,
                                TeachingRegistrationRepository teachingRegistrationRepository,
                                FollowClassRepository followClassRepository,
                                StaffRepository staffRepository,
                                MemberRepository memberRepository) {
        this.classSlotRepository = classSlotRepository;
        this.teachingRegistrationRepository = teachingRegistrationRepository;
        this.followClassRepository = followClassRepository;
        this.staffRepository = staffRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<ClassSlot> getAll() {
        return classSlotRepository.findAll();
    }

    @Override
    public Optional<ClassSlot> getById(Long id) {
        return classSlotRepository.findById(id);
    }

    @Override
    public ClassSlot create(ClassSlot classSlot) {
        return classSlotRepository.save(classSlot);
    }

    @Override
    public ClassSlot update(Long id, ClassSlot classSlot) {
        return classSlotRepository.findById(id)
                .map(existing -> {
                    existing.setStartTime(classSlot.getStartTime());
                    existing.setEndTime(classSlot.getEndTime());
                    existing.setLocation(classSlot.getLocation());
                    existing.setCapacity(classSlot.getCapacity());
                    existing.setStatus(classSlot.getStatus());
                    existing.setClassType(classSlot.getClassType());
                    return classSlotRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        classSlotRepository.deleteById(id);
    }
}
