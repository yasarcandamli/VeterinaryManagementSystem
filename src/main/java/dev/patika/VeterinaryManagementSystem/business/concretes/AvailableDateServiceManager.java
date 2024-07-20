package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.AvailableDateRepo;
import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateServiceManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;

    public AvailableDateServiceManager(AvailableDateRepo availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public AvailableDate get(long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        this.get(availableDate.getId()); //Control
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.availableDateRepo.findAll(pageable);
    }
}
