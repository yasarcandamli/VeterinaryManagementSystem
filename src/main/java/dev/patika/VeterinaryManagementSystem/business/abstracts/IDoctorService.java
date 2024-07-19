package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Doctor;
import org.springframework.data.domain.Page;

public interface IDoctorService {
    Doctor save(Doctor doctor);

    Doctor get(long id);

    Doctor update(Doctor doctor);

    boolean delete(long id);

    Page<Doctor> cursor(int page, int pageSize);
}
