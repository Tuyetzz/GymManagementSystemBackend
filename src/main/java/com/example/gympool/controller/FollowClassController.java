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

    // Lấy danh sách member đang follow 1 class template
    @GetMapping("/classtemplate/{templateId}")
    public ResponseEntity<List<FollowClass>> getByClassTemplate(@PathVariable Long templateId) {
        return ResponseEntity.ok(followClassService.getByClassTemplate(templateId));
    }

    // Member follow 1 lớp
    @PostMapping
    public ResponseEntity<FollowClass> followClass(@RequestBody FollowClass followClass) {
        return ResponseEntity.ok(followClassService.followClass(followClass));
    }

    // Member bỏ follow
    @DeleteMapping("/{id}") // Nhận ID từ URL, ví dụ: /api/follow-classes/123
    public ResponseEntity<String> unfollowClass(@PathVariable Long id) { // Dùng @PathVariable
        followClassService.unfollowClassById(id); // Gọi một service mới theo ID
        return ResponseEntity.ok("Unfollow successfully");
    }
}
