package bg.tihomir.hobby.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_clients")
public class AppClient extends UserEntity implements Serializable {


    private String fullName;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Test testResults;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<HobbyEntity> hobby_matches = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<HobbyEntity> saved_hobbies = new ArrayList<>();

    public AppClient() {
    }

    public AppClient(String firstName,
                     String lastName,
                     String username,
                     String email,
                     String password,
                     boolean isActive,
                     String imageUrl,
                     List<UserRoleEntity> roles,
                     String fullName) {
        super(firstName, lastName, username, email, password, isActive, imageUrl, roles);
        this.fullName = fullName;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (getFirstName() != null) {
            fullName.append(getFirstName());
        }

        if (getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }

    public AppClient setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<HobbyEntity> getHobby_matches() {
        return hobby_matches;
    }

    public AppClient setHobby_matches(List<HobbyEntity> hobby_matches) {
        this.hobby_matches = hobby_matches;
        return this;
    }

    public List<HobbyEntity> getSaved_hobbies() {
        return saved_hobbies;
    }

    public AppClient setSaved_hobbies(List<HobbyEntity> saved_hobbies) {
        this.saved_hobbies = saved_hobbies;
        return this;
    }

    public Test getTestResults() {
        return testResults;
    }

    public AppClient setTestResults(Test testResults) {
        this.testResults = testResults;
        return this;
    }
}
