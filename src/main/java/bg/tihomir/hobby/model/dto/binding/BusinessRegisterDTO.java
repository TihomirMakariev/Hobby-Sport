package bg.tihomir.hobby.model.dto.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BusinessRegisterDTO {

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

    public BusinessRegisterDTO() {
    }

    public Long getId() {
        return id;
    }

    public BusinessRegisterDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public BusinessRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BusinessRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public BusinessRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public BusinessRegisterDTO setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BusinessRegisterDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BusinessRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BusinessRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public BusinessRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BusinessRegisterDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
