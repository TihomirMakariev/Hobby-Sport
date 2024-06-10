package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.LocationEntity;
import bg.tihomir.hobby.model.enums.LocationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByName(LocationEnum locationEnum);

}
