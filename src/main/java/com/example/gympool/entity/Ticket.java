package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;   // ngày mua

    @Column(nullable = false, length = 20)
    private String status;   // ACTIVE, CANCELLED, EXPIRED

    @Column(name = "card_id", length = 50)
    private String cardId;   // thẻ vật lý gắn với ticket (nullable)

    // FK tới TicketType
    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    // FK tới Member
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
