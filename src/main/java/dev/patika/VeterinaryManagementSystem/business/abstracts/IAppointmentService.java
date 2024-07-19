package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import org.springframework.data.domain.Page;

public interface IAppointmentService {
    Appointment save(Appointment appointment);

    Appointment get(long id);

    Appointment update(Appointment appointment);

    boolean delete(long id);

    Page<Appointment> cursor(int page, int pageSize);
}
