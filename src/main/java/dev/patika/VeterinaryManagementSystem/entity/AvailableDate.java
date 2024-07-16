package dev.patika.VeterinaryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "available_dates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private long id;

    @NotNull
    @Column(name = "available_date")
    private LocalDate availableDate;

    @ManyToOne
    @JoinColumn(name = "available_date_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;
}
