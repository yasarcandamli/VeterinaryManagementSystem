package dev.patika.VeterinaryManagementSystem.core.config.modelMapper;

import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.entity.Animal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelManagerService implements IModelMapperService {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelManagerService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure() {
        // General configuration
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        // Specific DTO and Entity configurations
        this.modelMapper.typeMap(AnimalSaveRequest.class, Animal.class).addMappings(mapper -> {
            mapper.skip(Animal::setId);  // skip the id for Animal
        });
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }
}
