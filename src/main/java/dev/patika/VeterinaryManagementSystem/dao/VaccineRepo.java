package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByAnimalIdAndCodeAndNameAndProtectionEndDateAfter(
            Long animalId,
            String code,
            String name,
            LocalDate protectionEndDate
    );

    List<Vaccine> findByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate);
}
