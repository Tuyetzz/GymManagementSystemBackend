package com.example.gympool.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="staffs")
public class Staff extends User {
    private String position;
    private String specialize;
    private String hirePrice;
}
