package com.example.gympool.service;

import com.example.gympool.entity.PTAppointment;
import com.example.gympool.repository.PTAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final PTAppointmentRepository ptAppointmentRepository;
    private final NotificationService notificationService;
    @Scheduled(fixedRate = 300000) // Chạy mỗi 5 phút (300000 ms)
    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowEnd = now.plusMinutes(30); // Gửi thông báo cho lịch hẹn trong 30 phút tới
        List<PTAppointment> upcomingAppointments = ptAppointmentRepository.findUpcomingAppointments(now, windowEnd);
        for (PTAppointment appointment : upcomingAppointments) {
            // Gửi thông báo
            notificationService.sendAppointmentReminder(appointment);
            // Đánh dấu đã gửi thông báo
            appointment.setNotificationSent(true);
            ptAppointmentRepository.save(appointment);
        }
    }
}
