package com.example.gympool.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
