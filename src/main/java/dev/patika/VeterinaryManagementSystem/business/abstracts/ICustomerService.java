package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);

    Customer get(Long id);

    List<Customer> filterByName(String name);

    Customer update(Customer customer);

    boolean delete(Long id);

    Page<Customer> cursor(int page, int pageSize);
}
