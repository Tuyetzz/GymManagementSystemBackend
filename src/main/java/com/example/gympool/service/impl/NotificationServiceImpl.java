package com.example.gympool.service.impl;

import com.example.gympool.entity.PTAppointment;
import com.example.gympool.service.EmailService;
import com.example.gympool.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EmailService  emailService;
    public void sendAppointmentReminder(PTAppointment appointment){
        // Gửi email cho Member
        emailService.sendEmail(
                appointment.getPtPackageIssued().getMember().getEmail(), // Giả sử Member có trường email
                "Nhắc nhở lịch hẹn PT",
                String.format("Bạn có lịch hẹn PT vào lúc %s với huấn luyện viên %s. Vui lòng đến đúng giờ!",
                        appointment.getStartTime(), appointment.getStaff().getFullName())
        );

        // Gửi email cho Staff
        emailService.sendEmail(
                appointment.getStaff().getEmail(), // Giả sử Staff có trường email
                "Nhắc nhở lịch hẹn PT",
                String.format("Bạn có lịch hẹn PT với học viên %s vào lúc %s. Vui lòng chuẩn bị!",
                        appointment.getPtPackageIssued().getMember().getFullName(), appointment.getStartTime()));
    }


 }
