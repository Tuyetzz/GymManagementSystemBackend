package com.example.gympool.repository;

import com.example.gympool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndIsDeletedFalse(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByFullName(String fullName);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role IN ('STAFF', 'RECEPTIONIST', 'MANAGER') AND u.isDeleted = false")
    List<User> findAllStaffs();

    Optional<User> findByIdAndIsDeletedFalse(Long id);

    List<User> findAllByRoleAndIsDeletedFalse(String role);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void softDeleteById(@Param("id") Long id);
}
