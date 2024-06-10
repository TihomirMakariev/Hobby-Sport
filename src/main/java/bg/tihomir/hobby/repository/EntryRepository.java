package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findAllByAboId(Long id);
}
