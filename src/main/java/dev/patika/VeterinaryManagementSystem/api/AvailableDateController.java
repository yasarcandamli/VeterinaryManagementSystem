package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entity.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper, IDoctorService doctorService) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        AvailableDate saveAvailableDate =this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);

        Doctor doctor = this.doctorService.get(availableDateSaveRequest.getDoctorId());
        saveAvailableDate.setDoctor(doctor);

        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }
}
