package com.example.gympool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Long cost;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private LocalDate expiryDate;
    private String description;
}
