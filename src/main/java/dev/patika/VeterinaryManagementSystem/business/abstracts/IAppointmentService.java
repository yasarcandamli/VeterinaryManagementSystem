package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import org.springframework.data.domain.Page;

public interface IAppointmentService {
    Appointment save(Appointment appointment);

    Appointment get(int id);

    Appointment update(Appointment appointment);

    boolean delete(int id);

    Page<Appointment> cursor(int page, int pageSize);
}
