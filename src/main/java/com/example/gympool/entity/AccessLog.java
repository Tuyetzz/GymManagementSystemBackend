package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String deviceId;   // mã thiết bị quẹt/ camera

    @Column(nullable = false)
    private LocalDateTime accessTime;  // thời điểm quẹt/scan

    @Column(nullable = false, length = 50)
    private String locationType;   // GYM, POOL... (có thể đổi sang Enum)

    @Column(nullable = false, length = 20)
    private String accessMethod;   // FACE, CARD (có thể đổi sang Enum)

    @Column(nullable = false, length = 20)
    private String result;   // SUCCESS, DENIED

    private String reason;   // EXPIRED, NO_ENTITLEMENT, CARD_NOT_FOUND...

    // FK tới Member
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
