package com.example.gympool.service;

import com.example.gympool.entity.Member;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member createMember(Member member);
    Member updateMember(Long id, Member member);
    void deleteMember(Long id);
}
