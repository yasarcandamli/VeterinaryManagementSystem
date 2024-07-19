package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Vaccine;
import org.springframework.data.domain.Page;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);

    Vaccine get(long id);

    Vaccine update(Vaccine vaccine);

    boolean delete(long id);

    Page<Vaccine> cursor(int page, int pageSize);
}
