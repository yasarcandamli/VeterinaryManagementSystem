package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    private long id;
    private LocalDateTime appointmentDate;
    private long animalId;
    private long doctorId;
}
