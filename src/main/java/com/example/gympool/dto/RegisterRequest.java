package com.example.gympool.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private Date dob;
    private String gender;
    private String phone;
    private String role;
}
