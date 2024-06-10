package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.Abo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboRepository extends JpaRepository<Abo, Long> {

    List<Abo> findByClientId(Long id);

    List<Abo> findAllByBusinessOwnerId(Long id);

    List<Abo> findByHobbyId(Long id);
}
