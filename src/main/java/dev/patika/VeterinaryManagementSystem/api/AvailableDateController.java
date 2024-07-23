package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availableDate.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entity.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entity.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/available_dates")
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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<AvailableDateResponse> availableDateResponsePage = availableDateService.cursor(page, pageSize)
                .map(availableDate -> {
                    AvailableDateResponse response = modelMapper.forResponse().map(availableDate, AvailableDateResponse.class);
                    return response;
                });

        return ResultHelper.cursor(availableDateResponsePage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") Long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);
    }

    @GetMapping("/filterByDoctorId/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> filterByDoctorId(@PathVariable("doctorId") Long doctorId) {
        List<AvailableDate> availableDateList = this.availableDateService.findByDoctorId(doctorId);
        List<AvailableDateResponse> availableDateResponseList = availableDateList.stream()
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(availableDateResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate existingAvailableDate = this.availableDateService.get(availableDateUpdateRequest.getId());

        if (availableDateUpdateRequest.getDoctorId() != null) {
            Doctor doctor = this.doctorService.get(availableDateUpdateRequest.getDoctorId());
            existingAvailableDate.setDoctor(doctor);
        }

        this.modelMapper.forRequest().getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.forRequest().map(availableDateUpdateRequest, existingAvailableDate);

        AvailableDate updatedAvailableDate = this.availableDateService.update(existingAvailableDate);

        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(updatedAvailableDate, AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }
}
