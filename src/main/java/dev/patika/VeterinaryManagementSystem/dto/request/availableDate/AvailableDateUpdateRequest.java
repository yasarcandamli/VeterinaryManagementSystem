package dev.patika.VeterinaryManagementSystem.dto.request.availableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {
    private long id;
    private LocalDate availableDate;
    private long doctorId;
}
