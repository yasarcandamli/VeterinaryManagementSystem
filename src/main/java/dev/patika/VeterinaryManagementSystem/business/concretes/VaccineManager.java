package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.exception.VaccineConflictException;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.VaccineRepo;
import dev.patika.VeterinaryManagementSystem.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        List<Vaccine> existingVaccines = this.vaccineRepo.findByAnimalIdAndCodeAndNameAndProtectionEndDateAfter(
                vaccine.getAnimal().getId(),
                vaccine.getCode(),
                vaccine.getName(),
                vaccine.getProtectionStartDate()
        );

        if (!existingVaccines.isEmpty()) {
            throw new VaccineConflictException(Messages.VACCINE_CONFLICT);
        }
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(Long id) {
        return this.vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        this.get(vaccine.getId()); //Control
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(Long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return true;
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public List<Vaccine> getVaccinesByProtectionEndDateBetween(LocalDate startDate, LocalDate endDate) {
        return this.vaccineRepo.findByProtectionEndDateBetween(startDate, endDate);
    }
}
