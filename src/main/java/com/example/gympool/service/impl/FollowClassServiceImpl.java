package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassSchedule;
import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.ClassScheduleRepository;
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
    private final ClassScheduleRepository classScheduleRepository;

    public FollowClassServiceImpl(FollowClassRepository followClassRepository,
                                  MemberRepository memberRepository,
                                  ClassScheduleRepository classScheduleRepository) {
        this.followClassRepository = followClassRepository;
        this.memberRepository = memberRepository;
        this.classScheduleRepository = classScheduleRepository;
    }

    @Override
    public List<FollowClass> getByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        return followClassRepository.findByMember(member);
    }

    @Override
    public List<FollowClass> getByClassschedule(Long scheduleId) {
        ClassSchedule schedule = classScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Classschedule not found with id: " + scheduleId));
        return followClassRepository.findByClassSchedule(schedule);
    }

    @Override
    public FollowClass followClass(FollowClass followClass) {
        Long scheduleId = followClass.getClassSchedule().getId();
        Long memberId = followClass.getMember().getId();

        ClassSchedule schedule = classScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Classschedule not found with id: " + scheduleId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        // Check đã follow chưa
        followClassRepository.findByMember(member).stream()
                .filter(fc -> fc.getClassSchedule().getId().equals(scheduleId))
                .findFirst()
                .ifPresent(fc -> { throw new RuntimeException("Already followed this class"); });

        FollowClass newFollow = new FollowClass();
        newFollow.setClassSchedule(schedule);
        newFollow.setMember(member);
        newFollow.setFollowDate(new Date());

        return followClassRepository.save(newFollow);
    }

    @Override
    public void unfollowClass(FollowClass followClass) {
        Long scheduleId = followClass.getClassSchedule().getId();
        Long memberId = followClass.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        followClassRepository.findByMember(member).stream()
                .filter(fc -> fc.getClassSchedule().getId().equals(scheduleId))
                .findFirst()
                .ifPresentOrElse(
                        followClassRepository::delete,
                        () -> { throw new RuntimeException("Follow not found for this class"); }
                );
    }

}
