package bg.tihomir.hobby.model.dto.view;

import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;

import java.math.BigDecimal;

public class SearchHobbyView {

    private Long id;
    private String name;
    private String description;
    private CategoryNameEnum category;
    private BigDecimal price;
    private LocationEnum location;
    private String imgUrlMain;
    private String imgUrlOne;

    private String imgUrlTwo;

    private String imgUrlThree;

    public SearchHobbyView() {
    }

    public Long getId() {
        return id;
    }

    public SearchHobbyView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SearchHobbyView setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SearchHobbyView setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public SearchHobbyView setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SearchHobbyView setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public SearchHobbyView setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getImgUrlMain() {
        return imgUrlMain;
    }

    public SearchHobbyView setImgUrlMain(String imgUrlMain) {
        this.imgUrlMain = imgUrlMain;
        return this;
    }

    public String getImgUrlOne() {
        return imgUrlOne;
    }

    public SearchHobbyView setImgUrlOne(String imgUrlOne) {
        this.imgUrlOne = imgUrlOne;
        return this;
    }

    public String getImgUrlTwo() {
        return imgUrlTwo;
    }

    public SearchHobbyView setImgUrlTwo(String imgUrlTwo) {
        this.imgUrlTwo = imgUrlTwo;
        return this;
    }

    public String getImgUrlThree() {
        return imgUrlThree;
    }

    public SearchHobbyView setImgUrlThree(String imgUrlThree) {
        this.imgUrlThree = imgUrlThree;
        return this;
    }
}
