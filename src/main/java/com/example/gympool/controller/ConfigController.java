package com.example.gympool.controller;

import com.example.gympool.entity.Config;
import com.example.gympool.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    // 1️⃣ Upload banner mới
    @PostMapping("/banner")
    public ResponseEntity<Config> uploadBanner(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String name
    ) {
        return ResponseEntity.ok(configService.uploadBanner(file, name));
    }

    // 2️⃣ Toggle trạng thái hiển thị
    @PatchMapping("/banner/{id}/toggle")
    public ResponseEntity<Config> togglePublic(@PathVariable Long id) {
        return ResponseEntity.ok(configService.togglePublic(id));
    }

    // 3️⃣ Xóa banner
    @DeleteMapping("/banner/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable Long id) {
        configService.deleteBanner(id);
        return ResponseEntity.ok("Banner deleted successfully");
    }

    // 4️⃣ Lấy tất cả banner (cho admin)
    @GetMapping("/banner/all")
    public ResponseEntity<List<Config>> getAllBanners() {
        return ResponseEntity.ok(configService.getAllBanners());
    }

    // 5️⃣ Lấy banner công khai (hiển thị ở homepage)
    @GetMapping("/banner/public")
    public ResponseEntity<List<Config>> getPublicBanners() {
        return ResponseEntity.ok(configService.getPublicBanners());
    }
}
