package com.example.gympool.service;

import com.example.gympool.entity.FollowClass;
import java.util.List;

public interface FollowClassService {
    List<FollowClass> getByMember(Long memberId);
    List<FollowClass> getByClassSlot(Long slotId);

    // Member follow 1 lớp
    FollowClass followClass(FollowClass  followClass);

    // Member bỏ follow
    void unfollowClass(FollowClass  followClass);
}
