package com.example.gympool.repository;

import com.example.gympool.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    List<Config> findByKeyStartingWith(String prefix);
    List<Config> findByIsPublicTrue();
}
