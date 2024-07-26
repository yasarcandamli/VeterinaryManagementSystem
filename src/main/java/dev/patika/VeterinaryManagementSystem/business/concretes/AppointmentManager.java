package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.exception.*;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.AppointmentRepo;
import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final AvailableDateManager availableDateManager;

    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateManager availableDateManager) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateManager = availableDateManager;
    }

    @Override
    public Appointment save(Appointment appointment) {
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        Long doctorId = appointment.getDoctor().getId();

        if (!isOnTheHour(appointmentDate)) {
            throw new AppointmentHourException(Messages.APPOINTMENT_HOUR);
        }

        List<AvailableDate> availableDateList = availableDateManager.findByDoctorId(doctorId);
        boolean isDoctorAvailable = availableDateList.stream()
                .anyMatch(date -> date.getAvailableDate().equals(appointmentDate.toLocalDate()));

        if (!isDoctorAvailable) {
            throw new DoctorNotAvailableException(Messages.DOCTOR_NOT_AVAILABLE);
        }

        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateBetween(
                doctorId,
                appointmentDate.withMinute(0).withSecond(0).withNano(0),
                appointmentDate.withMinute(59).withSecond(59).withNano(99)
        );

        if (!existingAppointments.isEmpty()) {
            throw new AppointmentConflictException(Messages.APPOINTMENT_CONFLICT);
        }
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public Appointment update(Appointment appointment) {
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        Long doctorId = appointment.getDoctor().getId();

        if (!isOnTheHour(appointmentDate)) {
            throw new AppointmentHourException(Messages.APPOINTMENT_HOUR);
        }

        List<AvailableDate> availableDateList = availableDateManager.findByDoctorId(doctorId);
        boolean isDoctorAvailable = availableDateList.stream()
                .anyMatch(date -> date.getAvailableDate().equals(appointmentDate.toLocalDate()));

        if (!isDoctorAvailable) {
            throw new DoctorNotAvailableException(Messages.DOCTOR_NOT_AVAILABLE);
        }

        List<Appointment> existingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateBetween(
                doctorId,
                appointmentDate.withMinute(0).withSecond(0).withNano(0),
                appointmentDate.withMinute(59).withSecond(59).withNano(99)
        );

        existingAppointments.removeIf(existingAppointment ->
                existingAppointment.getAppointmentDate().equals(appointmentDate)
        );

        if (!existingAppointments.isEmpty()) {
            throw new AppointmentConflictException(Messages.APPOINTMENT_CONFLICT);
        }

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
        if (this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end).isEmpty()){
            throw new NotFoundException(Messages.NOT_FOUND);
        }
        return this.appointmentRepo.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    @Override
    public List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime start, LocalDateTime end) {
        if (this.appointmentRepo.findByAnimalIdAndAppointmentDateBetween(animalId, start, end).isEmpty()){
            throw new NotFoundException(Messages.NOT_FOUND);
        }
        return this.appointmentRepo.findByAnimalIdAndAppointmentDateBetween(animalId, start, end);
    }

    @Override
    public boolean isOnTheHour(LocalDateTime dateTime) {
        return dateTime.getMinute() == 0 && dateTime.getSecond() == 0 && dateTime.getNano() == 0;
    }
}
