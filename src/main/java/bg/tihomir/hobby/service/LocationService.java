package bg.tihomir.hobby.service;

import bg.tihomir.hobby.handler.NotFoundException;
import bg.tihomir.hobby.model.entity.LocationEntity;
import bg.tihomir.hobby.model.enums.LocationEnum;
import bg.tihomir.hobby.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationEntity getLocationByName(LocationEnum locationEnum) {
        Optional<LocationEntity> location =
                this.locationRepository.findByName(locationEnum);
        if (location.isPresent()) {
            return location.get();
        }  else {
            throw new NotFoundException("Location with name " + locationEnum + " not found");
        }
    }
}
