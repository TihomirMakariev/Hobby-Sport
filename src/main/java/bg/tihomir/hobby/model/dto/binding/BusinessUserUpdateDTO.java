package bg.tihomir.hobby.model.dto.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BusinessUserUpdateDTO {

    private Long id;
    @Size(min = 3, max = 20, message = " must be between 3 and 20 symbols")
    private String username;
    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 30, message = " must be between 2 and 30 symbols")
    private String businessName;
    @Size(min = 3, max = 30, message = " must be between 3 and 30 symbols")
    private String address;
    @NotNull
    @Pattern(regexp = ".+@.+\\..+", message = "Email must be valid")
    private String email;
    private String imageUrl;

    @Size(min = 3, max = 20, message = " must be between 3 and 20 symbols")
    private String password;
    @Size(min = 3, max = 20, message = " must be between 3 and 20 symbols")
    private String confirmPassword;


    public BusinessUserUpdateDTO() {
    }

    public Long getId() {
        return id;
    }

    public BusinessUserUpdateDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public BusinessUserUpdateDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public BusinessUserUpdateDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BusinessUserUpdateDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public BusinessUserUpdateDTO setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BusinessUserUpdateDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BusinessUserUpdateDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BusinessUserUpdateDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BusinessUserUpdateDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public BusinessUserUpdateDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
