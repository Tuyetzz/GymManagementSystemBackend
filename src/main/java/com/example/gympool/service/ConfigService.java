package com.example.gympool.service;

import com.example.gympool.entity.Config;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConfigService {
    Config uploadBanner(MultipartFile file, String name);
    Config togglePublic(Long id);
    void deleteBanner(Long id);
    List<Config> getAllBanners();        // Lấy tất cả banner (cho admin)
    List<Config> getPublicBanners();     // Lấy banner công khai (cho homepage)
}
