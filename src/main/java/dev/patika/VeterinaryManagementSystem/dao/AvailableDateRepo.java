package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {
    List<AvailableDate> findByDoctorId(Long doctorId);
    Optional<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);
}
