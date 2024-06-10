package bg.tihomir.hobby.service;

import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import bg.tihomir.hobby.repository.BusinessOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;

    public BusinessOwnerService(BusinessOwnerRepository businessOwnerRepository) {
        this.businessOwnerRepository = businessOwnerRepository;
    }


    public BusinessOwnerEntity findByEmail(String email) {
        return this.businessOwnerRepository
                .findByEmail(email).orElse(null);
    }


}
