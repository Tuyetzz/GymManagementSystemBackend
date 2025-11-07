package com.example.gympool.dto;

import com.example.gympool.entity.Staff;
import com.example.gympool.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDetailResponse {
    // Các trường chung
    private Long id;
    private String email;
    private String fullName;
    private Date dob;       // <-- Thêm trường này
    private String gender;
    private String phone;
    private String role;

    // Các trường riêng (có thể null)
    private String position;
    private String specialize;
    private String hirePrice;

    // Constructor quan trọng
    public UserDetailResponse(User user) {
        // 1. Set các trường chung
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.dob = user.getDob(); // <-- Thêm trường này
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.role = user.getRole();

        // 2. Kiểm tra nếu là Staff thì set các trường riêng
        if (user instanceof Staff staff) {
            this.position = staff.getPosition();
            this.specialize = staff.getSpecialize();
            this.hirePrice = staff.getHirePrice();
        }

        // (Bạn có thể thêm instanceof cho Manager/Receptionist nếu họ có trường riêng)
    }
}