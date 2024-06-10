package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.AppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppClientRepository extends JpaRepository<AppClient, Long> {
    Optional<AppClient> findByEmail(String username);

}
