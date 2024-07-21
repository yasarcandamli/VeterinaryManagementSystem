package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IModelMapperService modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        Customer saveCustomer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerService.save(saveCustomer);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer, CustomerResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        Page<CustomerResponse> customerResponsePage = customerService.cursor(page, pageSize)
                .map(customer -> {
                    CustomerResponse response = modelMapper.forResponse().map(customer, CustomerResponse.class);
                    return response;
                });

        return ResultHelper.cursor(customerResponsePage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> get(@PathVariable("id") Long id) {
        Customer customer = this.customerService.get(id);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer, CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }

    @GetMapping("/filterByCustomerName")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> filterByCustomerName(@RequestParam("customerName") String name) {
        List<Customer> customerList = this.customerService.filterByName(name);
        List<CustomerResponse> customerResponseList = customerList.stream()
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(customerResponseList);
    }

    @GetMapping("/allAnimalsOfCustomer")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> allAnimalsOfCustomer(@RequestParam("customerId") Long customerId) {
        List<Animal> animalList = this.customerService.get(customerId).getAnimalList();
        List<AnimalResponse> animalResponseList = animalList.stream()
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.successList(animalResponseList);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        Customer existingCustomer = this.customerService.get(customerUpdateRequest.getId());

        this.modelMapper.forRequest().getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.forRequest().map(customerUpdateRequest, existingCustomer);

        Customer updatedCustomer = this.customerService.update(existingCustomer);

        CustomerResponse customerResponse = this.modelMapper.forResponse().map(updatedCustomer, CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }
}
