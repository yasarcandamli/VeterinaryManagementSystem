package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal saveAnimal =this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);

        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        saveAnimal.setCustomer(customer);

        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal, AnimalResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<AnimalResponse> animalResponsePage = animalService.cursor(page, pageSize)
                .map(animal -> {
                    AnimalResponse response = modelMapper.forResponse().map(animal, AnimalResponse.class);
                    return response;
                });

        return ResultHelper.cursor(animalResponsePage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") Long id) {
        Animal animal = this.animalService.get(id);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    @GetMapping("/filterByAnimalName")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> filterByAnimalName(@RequestParam("animalName") String name) {
        List<Animal> animalList = this.animalService.filterByName(name);
        List<AnimalResponse> animalResponseList = animalList.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(animalResponseList);
    }

    @GetMapping("/filterByCustomerId")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> filterByCustomerId(@RequestParam("customerId") Long customerId) {
        List<Animal> animalList = this.customerService.get(customerId).getAnimalList();
        List<AnimalResponse> animalResponseList = animalList.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(animalResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal existingAnimal = this.animalService.get(animalUpdateRequest.getId());

        this.modelMapper.forRequest().getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.forRequest().map(animalUpdateRequest, existingAnimal);

        Animal updatedAnimal = this.animalService.update(existingAnimal);

        AnimalResponse animalResponse = this.modelMapper.forResponse().map(updatedAnimal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.animalService.delete(id);
        return ResultHelper.ok();
    }
}
