package com.example.gympool.repository;

import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.Member;
import com.example.gympool.entity.ClassSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowClassRepository extends JpaRepository<FollowClass, Long> {
    List<FollowClass> findByMember(Member member);
    List<FollowClass> findByClassSlot(ClassSlot classSlot);
}
