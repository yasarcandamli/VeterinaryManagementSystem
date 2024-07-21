package dev.patika.VeterinaryManagementSystem.dto.response.availableDate;

import dev.patika.VeterinaryManagementSystem.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private long id;
    private LocalDate availableDate;
    private Doctor doctor;
}
