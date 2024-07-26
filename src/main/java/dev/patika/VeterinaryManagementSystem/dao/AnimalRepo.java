package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entity.Animal;
import dev.patika.VeterinaryManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findAllByName(String name);
    void deleteAllByCustomer(Customer customer);
    Optional<Animal> findByNameAndSpeciesAndBreedAndGenderAndDateOfBirth(String name, String species, String breed, String gender, LocalDate dateOfBirth);
}
