package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.CategoryEntity;
import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(CategoryNameEnum category);
}
