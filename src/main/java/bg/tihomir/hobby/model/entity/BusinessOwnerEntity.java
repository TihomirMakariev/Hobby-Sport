package bg.tihomir.hobby.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "business_owners")
public class BusinessOwnerEntity extends UserEntity{

    @Column(name = "business_name", nullable = false)
    private String businessName;
    @Column
    private String address;
    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL)
    private List<HobbyEntity> hobby_offers = new ArrayList<>();

    public BusinessOwnerEntity(String firstName,
                               String lastName,
                               String username,
                               String email,
                               String password,
                               boolean isActive,
                               String imageUrl,
                               List<UserRoleEntity> roles,
                               String businessName,
                               String address) {
        super(firstName, lastName, username, email, password, isActive, imageUrl, roles);
        this.businessName = businessName;
        this.address = address;
    }

    public BusinessOwnerEntity() {};

    public String getBusinessName() {
        return businessName;
    }

    public BusinessOwnerEntity setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BusinessOwnerEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<HobbyEntity> getHobby_offers() {
        return hobby_offers;
    }

    public BusinessOwnerEntity setHobby_offers(List<HobbyEntity> hobby_offers) {
        this.hobby_offers = hobby_offers;
        return this;
    }
}
