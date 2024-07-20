package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;

    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(long id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId()); //Control
        return this.customerRepo.save(customer);
    }

    @Override
    public boolean delete(long id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable);
    }
}
