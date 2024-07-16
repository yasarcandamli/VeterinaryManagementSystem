package dev.patika.VeterinaryManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private long id;

    @NotNull
    @Column(name = "doctor_name")
    private String name;

    @NotNull
    @Column(name = "doctor_phone")
    private String phone;

    @NotNull
    @Column(name = "doctor_mail")
    private String mail;

    @NotNull
    @Column(name = "doctor_address")
    private String address;

    @NotNull
    @Column(name = "doctor_city")
    private String city;
}
