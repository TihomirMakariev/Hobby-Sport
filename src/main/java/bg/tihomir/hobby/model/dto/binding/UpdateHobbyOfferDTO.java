package bg.tihomir.hobby.model.dto.binding;

import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class UpdateHobbyOfferDTO {

    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 symbols.")
    @NotNull(message = "Hobby name can not be empty.")
    private String name;
    @Size(min = 10, max = 1050, message = "Description must be between 10 and 1050 symbols")
    @NotNull(message = "You need to have a description")
    private String description;
    @NotNull(message = "You have to choose category")
    private CategoryNameEnum category;
    @NotNull(message = "You have to set price")
    private BigDecimal price;
    @NotNull(message = "You have to choose a location")
    private LocationEnum location;
    @NotNull(message = "You have to choose profile picture")
    private MultipartFile newImgUrlMain;
    private MultipartFile imgOne;
    private MultipartFile imgTwo;
    private MultipartFile imgThree;

    public String getName() {
        return name;
    }

    public UpdateHobbyOfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateHobbyOfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public UpdateHobbyOfferDTO setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UpdateHobbyOfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public UpdateHobbyOfferDTO setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public MultipartFile getNewImgUrlMain() {
        return newImgUrlMain;
    }

    public UpdateHobbyOfferDTO setNewImgUrlMain(MultipartFile newImgUrlMain) {
        this.newImgUrlMain = newImgUrlMain;
        return this;
    }

    public MultipartFile getImgOne() {
        return imgOne;
    }

    public UpdateHobbyOfferDTO setImgOne(MultipartFile imgOne) {
        this.imgOne = imgOne;
        return this;
    }

    public MultipartFile getImgTwo() {
        return imgTwo;
    }

    public UpdateHobbyOfferDTO setImgTwo(MultipartFile imgTwo) {
        this.imgTwo = imgTwo;
        return this;
    }

    public MultipartFile getImgThree() {
        return imgThree;
    }

    public UpdateHobbyOfferDTO setImgThree(MultipartFile imgThree) {
        this.imgThree = imgThree;
        return this;
    }
}
