package bg.tihomir.hobby.model.dto.view;

import bg.tihomir.hobby.model.entity.HobbyEntity;

import java.util.ArrayList;
import java.util.List;

public class AppClientView {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String imageUrl;
    private String fullName;

    private List<HobbyEntity> hobby_matches = new ArrayList<>();
    private List<HobbyEntity> saved_hobbies = new ArrayList<>();

    public AppClientView() {
    }

    public Long getId() {
        return id;
    }

    public AppClientView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppClientView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AppClientView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AppClientView setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AppClientView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AppClientView setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AppClientView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppClientView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<HobbyEntity> getHobby_matches() {
        return hobby_matches;
    }

    public AppClientView setHobby_matches(List<HobbyEntity> hobby_matches) {
        this.hobby_matches = hobby_matches;
        return this;
    }

    public List<HobbyEntity> getSaved_hobbies() {
        return saved_hobbies;
    }

    public AppClientView setSaved_hobbies(List<HobbyEntity> saved_hobbies) {
        this.saved_hobbies = saved_hobbies;
        return this;
    }
}
