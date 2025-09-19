package com.example.gympool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "import_bills")
public class ImportBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    private String paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Double total;

    // Quan hệ 1-nhiều với ImportedProduct
    @OneToMany(mappedBy = "importBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImportedProduct> importedProducts;

    // Nhiều ImportBill có thể từ 1 Provider
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    // Nhiều ImportBill có thể do 1 Manager lập
    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
}
