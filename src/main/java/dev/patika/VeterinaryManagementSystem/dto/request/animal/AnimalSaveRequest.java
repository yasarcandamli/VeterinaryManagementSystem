package dev.patika.VeterinaryManagementSystem.dto.request.animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    @NotNull(message = "name cannot be empty or null!")
    private String name;

    @NotNull(message = "species cannot be empty or null!")
    private String species;

    @NotNull(message = "breed cannot be empty or null!")
    private String breed;

    @NotNull(message = "gender cannot be empty or null!")
    private String gender;

    @NotNull(message = "color cannot be empty or null!")
    private String color;

    @NotNull(message = "dateOfBirth cannot be empty or null!")
    private LocalDate dateOfBirth;

    @NotNull(message = "customerId cannot be empty or null!")
    private Long customerId;
}
