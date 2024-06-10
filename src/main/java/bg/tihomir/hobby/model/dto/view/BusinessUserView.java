package bg.tihomir.hobby.model.dto.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BusinessUserView {


    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String businessName;
    private String address;
    private String email;
    private String imageUrl;
    private String password;

    public BusinessUserView() {
    }

    public Long getId() {
        return id;
    }

    public BusinessUserView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public BusinessUserView setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public BusinessUserView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BusinessUserView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public BusinessUserView setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BusinessUserView setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BusinessUserView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BusinessUserView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BusinessUserView setPassword(String password) {
        this.password = password;
        return this;
    }
}
