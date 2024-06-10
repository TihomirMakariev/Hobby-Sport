package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import bg.tihomir.hobby.model.entity.HobbyEntity;
import bg.tihomir.hobby.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HobbyRepository extends JpaRepository<HobbyEntity, Long>,
        JpaSpecificationExecutor<HobbyEntity> {
    List<HobbyEntity> findAllByBusinessOwner(BusinessOwnerEntity businessOwner);

    List<HobbyEntity> findAllByLocation(LocationEntity location);

}
