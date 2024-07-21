package dev.patika.VeterinaryManagementSystem.dto.request.availableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {
    @NotNull(message = "availableDate cannot be empty or null!")
    private LocalDate availableDate;

    @NotNull(message = "doctorId cannot be empty or null!")
    private long doctorId;
}
