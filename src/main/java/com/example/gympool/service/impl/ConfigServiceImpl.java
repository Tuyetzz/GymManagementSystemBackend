package com.example.gympool.service.impl;

import com.example.gympool.entity.Config;
import com.example.gympool.repository.ConfigRepository;
import com.example.gympool.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;
    private static final String BANNER_DIR = "image/banners/";

    @Override
    public Config uploadBanner(MultipartFile file, String name) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Thư mục lưu banner (tương đối với project root)
            Path folderPath = Paths.get(BANNER_DIR).toAbsolutePath();
            if (!folderPath.toFile().exists()) {
                folderPath.toFile().mkdirs();
            }

            // Lấy tên gốc và phần mở rộng
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new RuntimeException("Invalid file name");
            }
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Tạo tên file mới
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeName = (name != null && !name.trim().isEmpty())
                    ? name.trim().replaceAll("\\s+", "_")
                    : originalFilename.replaceAll("\\s+", "_");
            String fileName = timestamp + "_" + safeName + extension;

            Path filePath = folderPath.resolve(fileName);

            // Lưu file vật lý
            file.transferTo(filePath.toFile());

            // Lưu DB (lưu đường dẫn tương đối để dùng trên web)
            Config config = Config.builder()
                    .key("banner")
                    .value('/' + BANNER_DIR + fileName) // ví dụ "image/banners/20251027_171113_a.jpg"
                    .isPublic(false)
                    .build();

            return configRepository.save(config);

        } catch (IOException e) {
            throw new RuntimeException("Error saving banner: " + e.getMessage(), e);
        }
    }


    @Override
    public Config togglePublic(Long id) {
        Config config = configRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        config.setPublic(!config.isPublic());
        return configRepository.save(config);
    }

    @Override
    public void deleteBanner(Long id) {
        Config config = configRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        File file = new File("image/banners", Paths.get(config.getValue()).getFileName().toString());
        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Failed to delete banner file: " + file.getAbsolutePath());
            }
        }

        configRepository.delete(config);
    }


    @Override
    public List<Config> getAllBanners() {
        // Lấy tất cả config có key = "banner"
        return configRepository.findByKeyStartingWith("banner");
    }

    @Override
    public List<Config> getPublicBanners() {
        // Lấy banner công khai
        return configRepository.findByIsPublicTrue().stream()
                .filter(c -> c.getKey().equals("banner"))
                .toList();
    }
}
