package bg.tihomir.hobby.model.dto.binding;

import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;
import jakarta.validation.constraints.NotNull;

public class TestDTO {

    private CategoryNameEnum categoryOne;
    private CategoryNameEnum categoryTwo;
    private CategoryNameEnum categoryThree;
    private CategoryNameEnum categoryFour;
    private CategoryNameEnum categoryFive;
    private CategoryNameEnum categorySix;
    private CategoryNameEnum categorySeven;
    private LocationEnum location;

    public TestDTO() {
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategoryOne() {
        return categoryOne;
    }

    public void setCategoryOne(CategoryNameEnum categoryOne) {
        this.categoryOne = categoryOne;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategoryTwo() {
        return categoryTwo;
    }

    public void setCategoryTwo(CategoryNameEnum categoryTwo) {
        this.categoryTwo = categoryTwo;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategoryThree() {
        return categoryThree;
    }

    public void setCategoryThree(CategoryNameEnum categoryThree) {
        this.categoryThree = categoryThree;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategoryFour() {
        return categoryFour;
    }

    public void setCategoryFour(CategoryNameEnum categoryFour) {
        this.categoryFour = categoryFour;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategoryFive() {
        return categoryFive;
    }

    public void setCategoryFive(CategoryNameEnum categoryFive) {
        this.categoryFive = categoryFive;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategorySix() {
        return categorySix;
    }

    public void setCategorySix(CategoryNameEnum categorySix) {
        this.categorySix = categorySix;
    }

    @NotNull(message = "You have to choose an answer")
    public CategoryNameEnum getCategorySeven() {
        return categorySeven;
    }

    public void setCategorySeven(CategoryNameEnum categorySeven) {
        this.categorySeven = categorySeven;
    }

    @NotNull(message = "You have to choose a location")
    public LocationEnum getLocation() {
        return location;
    }

    public void setLocation(LocationEnum location) {
        this.location = location;
    }
}