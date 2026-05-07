package federation.agricole.api.service;

import federation.agricole.api.dto.CollectivityInformationRest;
import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectivityInformationService {
    CollectivityRepository collectivityRepository;

    public CollectivityInformationService(CollectivityRepository collectivityRepository) {
        this.collectivityRepository = collectivityRepository;
    }

    public Collectivity updateInformation(String collectivityId, CollectivityInformationRest dto) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }

        if (dto.getName() != null && collectivityRepository.existsByNameAndIdNot(dto.getName(), collectivityId)) {
            throw new BadRequestException("Collectivity name '" + dto.getName() + "' is already used by another collectivity");
        }

        if (dto.getNumber() != null && collectivityRepository.existsByNumberAndIdNot(dto.getNumber(), collectivityId)) {
            throw new BadRequestException("Collectivity number " + dto.getNumber() + " is already used by another collectivity");
        }

        return collectivityRepository.updateInformation(collectivityId, dto.getName(), dto.getNumber());
    }
}
