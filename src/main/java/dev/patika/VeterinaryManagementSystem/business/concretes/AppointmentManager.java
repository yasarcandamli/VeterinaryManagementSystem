package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.exception.ConflictException;
import dev.patika.VeterinaryManagementSystem.core.exception.IllegalArgumentException;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.AppointmentRepo;
import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        Long doctorId = appointment.getDoctor().getId();
        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateBetween(
                doctorId,
                appointmentDate.withMinute(0).withSecond(0).withNano(0),
                appointmentDate.withMinute(59).withSecond(59).withNano(999999999)
        );

        if (!existingAppointments.isEmpty()) {
            throw new IllegalArgumentException(Messages.ILLEGAL_ARGUMENT);
        }
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId()); //Control
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public boolean delete(Long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.appointmentRepo.findAll(pageable);
    }

    @Override
    public List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }
}
