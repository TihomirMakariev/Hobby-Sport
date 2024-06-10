package bg.tihomir.hobby.service;

import bg.tihomir.hobby.handler.NotFoundException;
import bg.tihomir.hobby.model.entity.CategoryEntity;
import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity findByName(CategoryNameEnum category) {
        Optional<CategoryEntity> byName =
                this.categoryRepository.findByName(category);

        if (byName.isPresent()) {
            return byName.get();
        } else {
            throw new NotFoundException("Category with this name not found");
        }
    }
}
