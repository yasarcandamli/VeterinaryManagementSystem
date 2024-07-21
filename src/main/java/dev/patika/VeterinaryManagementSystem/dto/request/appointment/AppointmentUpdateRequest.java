package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
}
