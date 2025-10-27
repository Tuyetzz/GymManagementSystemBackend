package com.example.gympool.repository;

import com.example.gympool.entity.ClassSchedule;
import com.example.gympool.entity.ClassTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassTemplateId(Long classTemplateId);
    List<ClassSchedule> findByStatus(String status);
    List<ClassSchedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT s FROM ClassSchedule s WHERE s.room.id = :roomId AND s.startTime BETWEEN :startOfDay AND :endOfDay")
    List<ClassSchedule> findSchedulesForRoomAndDate(@Param("roomId") Long roomId,
                                                    @Param("startOfDay") LocalDateTime startOfDay,
                                                    @Param("endOfDay") LocalDateTime endOfDay);

}
