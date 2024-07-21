package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {
    @NotNull(message = "appointmentDate cannot be empty or null!")
    private LocalDateTime appointmentDate;

    @NotNull(message = "animalId cannot be empty or null!")
    private Long animalId;

    @NotNull(message = "doctorId cannot be empty or null!")
    private Long doctorId;
}
