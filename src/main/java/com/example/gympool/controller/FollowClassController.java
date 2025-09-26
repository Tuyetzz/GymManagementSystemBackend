package com.example.gympool.controller;

import com.example.gympool.entity.FollowClass;
import com.example.gympool.service.FollowClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow-classes")
public class FollowClassController {

    private final FollowClassService followClassService;

    public FollowClassController(FollowClassService followClassService) {
        this.followClassService = followClassService;
    }

    // Lấy danh sách lớp mà 1 member đã follow
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<FollowClass>> getByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(followClassService.getByMember(memberId));
    }

    // Lấy danh sách member đang follow 1 class slot
    @GetMapping("/classslot/{slotId}")
    public ResponseEntity<List<FollowClass>> getByClassSlot(@PathVariable Long slotId) {
        return ResponseEntity.ok(followClassService.getByClassSlot(slotId));
    }

    @PostMapping
    public ResponseEntity<FollowClass> followClass(@RequestBody FollowClass followClass) {
        return ResponseEntity.ok(followClassService.followClass(followClass));
    }

    @DeleteMapping
    public ResponseEntity<String> unfollowClass(@RequestBody FollowClass followClass) {
        followClassService.unfollowClass(followClass);
        return ResponseEntity.ok("Unfollow successfully");
    }

}
