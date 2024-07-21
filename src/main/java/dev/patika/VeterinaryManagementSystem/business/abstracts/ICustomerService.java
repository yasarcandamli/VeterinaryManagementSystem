package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);

    Customer get(long id);

    List<Customer> filterByName(String name);

    Customer update(Customer customer);

    boolean delete(long id);

    Page<Customer> cursor(int page, int pageSize);
}
