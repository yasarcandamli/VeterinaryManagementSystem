package dev.patika.VeterinaryManagementSystem.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {
    @NotNull(message = "name cannot be empty or null!")
    private String name;

    @NotNull(message = "code cannot be empty or null!")
    private String code;

    @NotNull(message = "protectionStartDate cannot be empty or null!")
    private LocalDate protectionStartDate;

    @NotNull(message = "protectionStartDate cannot be empty or null!")
    private LocalDate protectionEndDate;

    @NotNull(message = "animalId cannot be empty or null!")
    private Long animalId;
}
