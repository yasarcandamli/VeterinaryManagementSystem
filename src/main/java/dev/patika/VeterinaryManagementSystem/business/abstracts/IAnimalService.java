package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entity.Animal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal);

    Animal get(long id);

    List<Animal> filterByName(String name);

    Animal update(Animal animal);

    boolean delete(long id);

    Page<Animal> cursor(int page, int pageSize);
}
