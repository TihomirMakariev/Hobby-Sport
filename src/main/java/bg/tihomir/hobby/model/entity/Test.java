package bg.tihomir.hobby.model.entity;

import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "test_results")
public class Test extends BaseEntity {

    @OneToOne
    private AppClient appClient;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_one")
    private CategoryNameEnum categoryOne;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_two")
    private CategoryNameEnum categoryTwo;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_three")
    private CategoryNameEnum categoryThree;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_four")
    private CategoryNameEnum categoryFour;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_five")
    private CategoryNameEnum categoryFive;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_six")
    private CategoryNameEnum categorySix;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_seven")
    private CategoryNameEnum categorySeven;
    @Enumerated(EnumType.STRING)
    private LocationEnum location;

    public Test() {
    }

    public AppClient getAppClient() {
        return appClient;
    }

    public Test setAppClient(AppClient appClient) {
        this.appClient = appClient;
        return this;
    }

    public CategoryNameEnum getCategoryOne() {
        return categoryOne;
    }

    public Test setCategoryOne(CategoryNameEnum categoryOne) {
        this.categoryOne = categoryOne;
        return this;
    }

    public CategoryNameEnum getCategoryTwo() {
        return categoryTwo;
    }

    public Test setCategoryTwo(CategoryNameEnum categoryTwo) {
        this.categoryTwo = categoryTwo;
        return this;
    }

    public CategoryNameEnum getCategoryThree() {
        return categoryThree;
    }

    public Test setCategoryThree(CategoryNameEnum categoryThree) {
        this.categoryThree = categoryThree;
        return this;
    }

    public CategoryNameEnum getCategoryFour() {
        return categoryFour;
    }

    public Test setCategoryFour(CategoryNameEnum categoryFour) {
        this.categoryFour = categoryFour;
        return this;
    }

    public CategoryNameEnum getCategoryFive() {
        return categoryFive;
    }

    public Test setCategoryFive(CategoryNameEnum categoryFive) {
        this.categoryFive = categoryFive;
        return this;
    }

    public CategoryNameEnum getCategorySix() {
        return categorySix;
    }

    public Test setCategorySix(CategoryNameEnum categorySix) {
        this.categorySix = categorySix;
        return this;
    }

    public CategoryNameEnum getCategorySeven() {
        return categorySeven;
    }

    public Test setCategorySeven(CategoryNameEnum categorySeven) {
        this.categorySeven = categorySeven;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public Test setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }
}
