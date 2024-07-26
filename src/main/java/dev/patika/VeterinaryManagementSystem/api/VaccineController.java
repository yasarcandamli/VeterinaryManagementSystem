package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
private final IVaccineService vaccineService;
private final IModelMapperService modelMapper;
private final IAnimalService animalService;

    public VaccineController(IVaccineService vaccineService, IModelMapperService modelMapper, IAnimalService animalService) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.animalService = animalService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        Vaccine saveVaccine =this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);

        Animal animal = this.animalService.get(vaccineSaveRequest.getAnimalId());
        saveVaccine.setAnimal(animal);

        this.vaccineService.save(saveVaccine);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<VaccineResponse> vaccineResponsePage = vaccineService.cursor(page, pageSize)
                .map(vaccine -> {
                    VaccineResponse response = modelMapper.forResponse().map(vaccine, VaccineResponse.class);
                    return response;
                });

        return ResultHelper.cursor(vaccineResponsePage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @GetMapping("/allVaccinesOfAnimal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> animalVaccineList(@PathVariable("animalId") Long animalId) {
        List<Vaccine> vaccineList = this.animalService.get(animalId).getVaccineList();
        List<VaccineResponse> vaccineResponseList = vaccineList.stream()
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(vaccineResponseList);
    }

    @GetMapping("/expiring")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByProtectionEndDateBetween(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate
    ) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByProtectionEndDateBetween(startDate, endDate);
        List<VaccineResponse> response = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(response);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine existingVaccine = this.vaccineService.get(vaccineUpdateRequest.getId());

        if (vaccineUpdateRequest.getAnimalId() != null) {
            Animal animal = this.animalService.get(vaccineUpdateRequest.getAnimalId());
            existingVaccine.setAnimal(animal);
        }

        this.modelMapper.forRequest().getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.forRequest().map(vaccineUpdateRequest, existingVaccine);

        Vaccine updatedVaccine = this.vaccineService.update(existingVaccine);

        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(updatedVaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }
}
