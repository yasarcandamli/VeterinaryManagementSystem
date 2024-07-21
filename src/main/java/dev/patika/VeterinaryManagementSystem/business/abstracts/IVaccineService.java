package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Vaccine;
import org.springframework.data.domain.Page;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);

    Vaccine get(Long id);

    Vaccine update(Vaccine vaccine);

    boolean delete(Long id);

    Page<Vaccine> cursor(int page, int pageSize);
}
