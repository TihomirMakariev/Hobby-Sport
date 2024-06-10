package bg.tihomir.hobby.model.dto.binding;

import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class HobbyOfferDTO {
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
    private MultipartFile imgMain;
    private MultipartFile imgOne;
    private MultipartFile imgTwo;
    private MultipartFile imgThree;

    public HobbyOfferDTO() {
    }

    public String getName() {
        return name;
    }

    public HobbyOfferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HobbyOfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public HobbyOfferDTO setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public HobbyOfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public HobbyOfferDTO setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public MultipartFile getImgMain() {
        return imgMain;
    }

    public HobbyOfferDTO setImgMain(MultipartFile imgMain) {
        this.imgMain = imgMain;
        return this;
    }

    public MultipartFile getImgOne() {
        return imgOne;
    }

    public HobbyOfferDTO setImgOne(MultipartFile imgOne) {
        this.imgOne = imgOne;
        return this;
    }

    public MultipartFile getImgTwo() {
        return imgTwo;
    }

    public HobbyOfferDTO setImgTwo(MultipartFile imgTwo) {
        this.imgTwo = imgTwo;
        return this;
    }

    public MultipartFile getImgThree() {
        return imgThree;
    }

    public HobbyOfferDTO setImgThree(MultipartFile imgThree) {
        this.imgThree = imgThree;
        return this;
    }
}
