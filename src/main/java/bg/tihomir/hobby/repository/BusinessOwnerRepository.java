package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessOwnerRepository extends JpaRepository<BusinessOwnerEntity, Long> {

   Optional<BusinessOwnerEntity> findByUsername(String username);
   Optional<BusinessOwnerEntity>findByEmail(String email);
   Optional<BusinessOwnerEntity> findByBusinessName(String businessName);


}
