package com.example.gympool.dto;

import com.example.gympool.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String gender;
    private String phone;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}
