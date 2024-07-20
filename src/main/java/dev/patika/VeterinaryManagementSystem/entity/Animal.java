package dev.patika.VeterinaryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private long id;

    @NotNull
    @Column(name = "animal_name")
    private String name;

    @NotNull
    @Column(name = "animal_species")
    private String species;

    @NotNull
    @Column(name = "animal_breed")
    private String breed;

    @NotNull
    @Column(name = "animal_gender")
    private String gender;

    @NotNull
    @Column(name = "animal_color")
    private String color;

    @NotNull
    @Column(name = "animal_date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "animal")
    private List<Vaccine> vaccineList;

    @ManyToOne
    @JoinColumn(name = "animal_customer_id", referencedColumnName = "customer_id")
    @JsonBackReference
    private Customer customer;

}
