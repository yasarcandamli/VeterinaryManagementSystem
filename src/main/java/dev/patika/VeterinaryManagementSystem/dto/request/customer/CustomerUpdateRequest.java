package dev.patika.VeterinaryManagementSystem.dto.request.customer;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    private Long id;
    private String name;
    private String phone;
    @Email
    private String mail;
    private String address;
    private String city;
}
