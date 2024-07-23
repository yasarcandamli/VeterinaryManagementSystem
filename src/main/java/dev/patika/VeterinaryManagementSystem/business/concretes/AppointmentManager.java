package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.exception.*;
import dev.patika.VeterinaryManagementSystem.core.exception.IllegalArgumentException;
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
                appointmentDate.withMinute(59).withSecond(59).withNano(999999999)
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
        Appointment existingAppointment = this.get(appointment.getId());

        // Yeni randevu tarihini ve doktor bilgilerini alın
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        Long doctorId = appointment.getDoctor().getId();
        LocalDateTime existingAppointmentDate = existingAppointment.getAppointmentDate();
        Long existingDoctorId = existingAppointment.getDoctor().getId();

        // Tarih ve doktorun değişip değişmediğini kontrol edin
        boolean isDateOrDoctorChanged = !appointmentDate.equals(existingAppointmentDate) || !doctorId.equals(existingDoctorId);

        if (!isOnTheHour(appointmentDate)) {
            throw new AppointmentHourException(Messages.APPOINTMENT_HOUR);
        }

        // Eğer tarih veya doktor değiştiyse, çakışmaları kontrol edin
        if (isDateOrDoctorChanged) {
            // Doktorun mevcut randevularını kontrol edin
            List<Appointment> conflictingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateBetween(
                    doctorId,
                    appointmentDate.withMinute(0).withSecond(0).withNano(0),
                    appointmentDate.withMinute(59).withSecond(59).withNano(999999999)
            );

            // Mevcut randevuyu çakışanlardan çıkarın
            conflictingAppointments.removeIf(a -> a.getId().equals(appointment.getId()));

            // Eğer çakışma varsa, bir hata fırlatın
            if (!conflictingAppointments.isEmpty()) {
                throw new AppointmentConflictException(Messages.APPOINTMENT_CONFLICT);
            }
        }

        // Randevuyu güncelleyin
        existingAppointment.setAppointmentDate(appointmentDate);
        existingAppointment.setDoctor(appointment.getDoctor());
        // Diğer gerekli alanları da güncelleyin

        return this.appointmentRepo.save(existingAppointment);
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

    @Override
    public List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime start, LocalDateTime end) {
        return this.appointmentRepo.findByAnimalIdAndAppointmentDateBetween(animalId, start, end);
    }

    @Override
    public boolean isOnTheHour(LocalDateTime dateTime) {
        return dateTime.getMinute() == 0 && dateTime.getSecond() == 0 && dateTime.getNano() == 0;
    }
}
