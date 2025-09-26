package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.ClassSlotRepository;
import com.example.gympool.repository.FollowClassRepository;
import com.example.gympool.repository.MemberRepository;
import com.example.gympool.service.FollowClassService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FollowClassServiceImpl implements FollowClassService {

    private final FollowClassRepository followClassRepository;
    private final MemberRepository memberRepository;
    private final ClassSlotRepository classSlotRepository;

    public FollowClassServiceImpl(FollowClassRepository followClassRepository,
                                  MemberRepository memberRepository,
                                  ClassSlotRepository classSlotRepository) {
        this.followClassRepository = followClassRepository;
        this.memberRepository = memberRepository;
        this.classSlotRepository = classSlotRepository;
    }

    @Override
    public List<FollowClass> getByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        return followClassRepository.findByMember(member);
    }

    @Override
    public List<FollowClass> getByClassSlot(Long slotId) {
        ClassSlot slot = classSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + slotId));
        return followClassRepository.findByClassSlot(slot);
    }

    @Override
    public FollowClass followClass(FollowClass followClass) {
        Long slotId = followClass.getClassSlot().getId();
        Long memberId = followClass.getMember().getId();

        ClassSlot slot = classSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("ClassSlot not found with id: " + slotId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        // Check đã follow chưa
        followClassRepository.findByMember(member).stream()
                .filter(fc -> fc.getClassSlot().getId().equals(slotId))
                .findFirst()
                .ifPresent(fc -> { throw new RuntimeException("Already followed this class"); });

        FollowClass newFollow = new FollowClass();
        newFollow.setClassSlot(slot);
        newFollow.setMember(member);
        newFollow.setFollowDate(new Date());

        return followClassRepository.save(newFollow);
    }

    @Override
    public void unfollowClass(FollowClass followClass) {
        Long slotId = followClass.getClassSlot().getId();
        Long memberId = followClass.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        followClassRepository.findByMember(member).stream()
                .filter(fc -> fc.getClassSlot().getId().equals(slotId))
                .findFirst()
                .ifPresentOrElse(
                        followClassRepository::delete,
                        () -> { throw new RuntimeException("Follow not found for this class"); }
                );
    }

}
