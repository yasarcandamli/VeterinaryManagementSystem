package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.exception.RecordAlreadyExistException;
import dev.patika.VeterinaryManagementSystem.core.utility.Messages;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;

    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        if (animalRepo.findByNameAndSpeciesAndBreedAndGenderAndDateOfBirth(
                animal.getName(),
                animal.getSpecies(),
                animal.getBreed(),
                animal.getGender(),
                animal.getDateOfBirth()).isPresent()) {
            throw new RecordAlreadyExistException(Messages.RECORD_ALREADY_EXIST);
        }
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(() -> new NotFoundException(Messages.NOT_FOUND));
    }

    @Override
    public List<Animal> filterByName(String name) {
        if (this.animalRepo.findAllByName(name).isEmpty()){
            throw new NotFoundException(Messages.NOT_FOUND);
        }
        return this.animalRepo.findAllByName(name);
    }

    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId()); //Control
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(Long id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.animalRepo.findAll(pageable);
    }
}
