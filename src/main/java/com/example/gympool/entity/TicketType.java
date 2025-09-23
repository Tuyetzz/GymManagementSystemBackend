package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket_types") // đặt tên bảng rõ ràng
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;   // VD: "Gym 1 tháng"

    @Column(nullable = false, length = 50)
    private String serviceType; // GYM, POOL... (sau này có thể đổi thành Enum)

    @Column(name = "duration_days", nullable = false)
    private int durationDays;   // số ngày hiệu lực

    @Column(nullable = false)
    private double price;   // giá tiền
}
