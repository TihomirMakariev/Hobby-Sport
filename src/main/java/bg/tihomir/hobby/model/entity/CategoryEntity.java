package bg.tihomir.hobby.model.entity;


import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryNameEnum name;

    public CategoryEntity(CategoryNameEnum categoryNameEnum) {
        this.name = categoryNameEnum;
    }

    public CategoryEntity() {
    }

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    public CategoryNameEnum getName() {
        return name;
    }

    public void setName(CategoryNameEnum name) {
        this.name = name;
    }
}