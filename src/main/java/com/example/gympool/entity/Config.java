package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_key")
    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;

    @Column(name = "is_public")
    private boolean isPublic;
}
