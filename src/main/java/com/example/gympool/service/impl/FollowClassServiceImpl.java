package com.example.gympool.service.impl;

import com.example.gympool.entity.ClassTemplate;
import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.Member;
import com.example.gympool.repository.ClassTemplateRepository;
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
    private final ClassTemplateRepository classTemplateRepository;

    public FollowClassServiceImpl(FollowClassRepository followClassRepository,
                                  MemberRepository memberRepository,
                                  ClassTemplateRepository classTemplateRepository) {
        this.followClassRepository = followClassRepository;
        this.memberRepository = memberRepository;
        this.classTemplateRepository = classTemplateRepository;
    }

    @Override
    public List<FollowClass> getByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        return followClassRepository.findByMember(member);
    }

    @Override
    public List<FollowClass> getByClassTemplate(Long templateId) {
        ClassTemplate template = classTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("ClassTemplate not found with id: " + templateId));
        return followClassRepository.findByClassTemplate(template);
    }

    @Override
    public FollowClass followClass(FollowClass followClass) {
        Long templateId = followClass.getClassTemplate().getId();
        Long memberId = followClass.getMember().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        ClassTemplate template = classTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("ClassTemplate not found with id: " + templateId));

        // Kiểm tra trùng follow
        boolean alreadyFollowed = followClassRepository.findByMember(member).stream()
                .anyMatch(fc -> fc.getClassTemplate().getId().equals(templateId));
        if (alreadyFollowed) {
            throw new RuntimeException("Already followed this class template");
        }

        FollowClass newFollow = new FollowClass();
        newFollow.setClassTemplate(template);
        newFollow.setMember(member);
        newFollow.setFollowDate(new Date());

        return followClassRepository.save(newFollow);
    }

    @Override
    public void unfollowClassById(Long followClassId) {
        // Kiểm tra xem record có tồn tại không trước khi xóa
        if (!followClassRepository.existsById(followClassId)) {
            throw new RuntimeException("Follow record not found with id: " + followClassId);
        }
        followClassRepository.deleteById(followClassId);
    }
}
