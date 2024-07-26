package dev.patika.VeterinaryManagementSystem.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull(message = "name cannot be empty or null!")
    private String name;

    @NotNull(message = "phone cannot be empty or null!")
    private String phone;

    @NotNull(message = "mail cannot be empty or null!")
    @Email
    private String mail;

    @NotNull(message = "address cannot be empty or null!")
    private String address;

    @NotNull(message = "city cannot be empty or null!")
    private String city;
}
