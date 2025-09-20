package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="members")
public class Member extends User {

    @Column(nullable = false, length = 50)
    private String membership;

    @Column(nullable = false)
    private Date joinDate;

    private String status;
    private String faceId;
    private String cardId;
}
