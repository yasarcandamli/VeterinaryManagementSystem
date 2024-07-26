package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import dev.patika.VeterinaryManagementSystem.entity.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IAnimalService animalService;
    private final IDoctorService doctorService;

    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IAnimalService animalService, IDoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        Appointment saveAppointment =this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);

        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimalId());
        saveAppointment.setAnimal(animal);

        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());
        saveAppointment.setDoctor(doctor);

        this.appointmentService.save(saveAppointment);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAppointment, AppointmentResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<AppointmentResponse> appointmentResponsePage = appointmentService.cursor(page, pageSize)
                .map(appointment -> {
                    AppointmentResponse response = modelMapper.forResponse().map(appointment, AppointmentResponse.class);
                    return response;
                });

        return ResultHelper.cursor(appointmentResponsePage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        Appointment appointment = this.appointmentService.get(id);
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    @GetMapping("/filterByDoctorAndDateRange")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> filterByDoctorAndDateRange(
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        List<Appointment> appointmentList = this.appointmentService.findByDoctorIdAndAppointmentDateBetween(doctorId, startDate, endDate);
        List<AppointmentResponse> appointmentResponseList = appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(appointmentResponseList);
    }

    @GetMapping("/filterByAnimalAndDateRange")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> filterByAnimalAndDateRange(
            @RequestParam Long animalId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        List<Appointment> appointmentList = this.appointmentService.findByAnimalIdAndAppointmentDateBetween(animalId, startDate, endDate);
        List<AppointmentResponse> appointmentResponseList = appointmentList.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(appointmentResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment existingAppointment = this.appointmentService.get(appointmentUpdateRequest.getId());

        if (appointmentUpdateRequest.getDoctorId() != null) {
            Doctor doctor = this.doctorService.get(appointmentUpdateRequest.getDoctorId());
            existingAppointment.setDoctor(doctor);
        }

        if (appointmentUpdateRequest.getAnimalId() != null) {
            Animal animal = this.animalService.get(appointmentUpdateRequest.getAnimalId());
            existingAppointment.setAnimal(animal);
        }

        this.modelMapper.forRequest().getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.forRequest().map(appointmentUpdateRequest, existingAppointment);

        Appointment updatedAppointment = this.appointmentService.update(existingAppointment);

        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(updatedAppointment, AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }
}
