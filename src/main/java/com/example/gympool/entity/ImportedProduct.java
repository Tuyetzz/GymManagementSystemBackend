package com.example.gympool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="imported_products")
public class ImportedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Nhiều ImportedProduct thuộc về 1 ImportBill
    @ManyToOne
    @JoinColumn(name = "import_bill_id", nullable = false)
    @JsonIgnore
    private ImportBill importBill;
}
