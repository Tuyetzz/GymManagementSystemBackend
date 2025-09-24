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
@NoArgsConstructor
@AllArgsConstructor
@Table(name="staffassigned")
public class StaffAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int trainingSession;

    // Many StaffAssigned belong to one Staff
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    // Many StaffAssigned belong to one Bill
    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonIgnore
    private Bill bill;
}
