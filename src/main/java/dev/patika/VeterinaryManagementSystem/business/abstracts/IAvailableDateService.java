package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);

    AvailableDate get(Long id);

    AvailableDate update(AvailableDate availableDate);

    boolean delete(Long id);

    Page<AvailableDate> cursor(int page, int pageSize);

    public List<AvailableDate> findByDoctorId(Long doctorId);
}
