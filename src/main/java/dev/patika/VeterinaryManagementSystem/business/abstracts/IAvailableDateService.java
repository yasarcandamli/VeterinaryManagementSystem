package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import org.springframework.data.domain.Page;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);

    AvailableDate get(Long id);

    AvailableDate update(AvailableDate availableDate);

    boolean delete(Long id);

    Page<AvailableDate> cursor(int page, int pageSize);
}
