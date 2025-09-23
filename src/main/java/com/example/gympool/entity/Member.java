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
    private String membership;
    private Date joinDate;

    private String status;
    private String faceId;
    private String cardId;
}
