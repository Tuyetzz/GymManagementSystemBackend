package com.example.gympool.repository;

import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PTPackageRepository extends JpaRepository<PTPackage, Long> {
}
