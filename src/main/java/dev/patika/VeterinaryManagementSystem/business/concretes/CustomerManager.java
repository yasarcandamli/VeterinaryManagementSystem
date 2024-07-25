package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;
    private final AnimalRepo animalRepo;

    public CustomerManager(CustomerRepo customerRepo, AnimalRepo animalRepo) {
        this.customerRepo = customerRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(Long id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public List<Customer> filterByName(String name) {
        if (this.customerRepo.findAllByName(name).isEmpty()){
            throw new NotFoundException(Messages.NOT_FOUND);
        }
        return this.customerRepo.findAllByName(name);
    }

    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId()); //Control
        return this.customerRepo.save(customer);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Customer customer = this.get(id);
        this.animalRepo.deleteAllByCustomer(customer);
        this.customerRepo.delete(customer);
        return true;
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.customerRepo.findAll(pageable);
    }
}
