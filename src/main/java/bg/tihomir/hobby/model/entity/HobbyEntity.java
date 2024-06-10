package bg.tihomir.hobby.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "hobbies")
public class HobbyEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    private BusinessOwnerEntity businessOwner;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private LocationEntity location;

    @Column(name = "image_url_main")
    private String imgUrlMain;

    @Column(name = "image_url_one")
    private String imgUrlOne;

    @Column(name = "image_url_two")
    private String imgUrlTwo;

    @Column(name = "image_url_three")
    private String imgUrlThree;

    public HobbyEntity() {
    }

    public String getName() {
        return name;
    }

    public HobbyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HobbyEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public HobbyEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public BusinessOwnerEntity getBusinessOwner() {
        return businessOwner;
    }

    public HobbyEntity setBusinessOwner(BusinessOwnerEntity businessOwner) {
        this.businessOwner = businessOwner;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public HobbyEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public HobbyEntity setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public String getImgUrlMain() {
        return imgUrlMain;
    }

    public HobbyEntity setImgUrlMain(String imgUrl) {
        this.imgUrlMain = imgUrl;
        return this;
    }

    public String getImgUrlOne() {
        return imgUrlOne;
    }

    public HobbyEntity setImgUrlOne(String imgUrlOne) {
        this.imgUrlOne = imgUrlOne;
        return this;
    }

    public String getImgUrlTwo() {
        return imgUrlTwo;
    }

    public HobbyEntity setImgUrlTwo(String imgUrlTwo) {
        this.imgUrlTwo = imgUrlTwo;
        return this;
    }

    public String getImgUrlThree() {
        return imgUrlThree;
    }

    public HobbyEntity setImgUrlThree(String imgUrlThree) {
        this.imgUrlThree = imgUrlThree;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HobbyEntity hobby = (HobbyEntity) o;
        return Objects.equals(getId(), hobby.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "HobbyEntity{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
