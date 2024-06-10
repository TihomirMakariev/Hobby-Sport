package bg.tihomir.hobby.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;

    public AppUserDetails(Long id, String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public AppUserDetails setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AppUserDetails setId(Long id) {
        this.id = id;
        return this;
    }
    public String getLastName() {
        return lastName;
    }

    public AppUserDetails setLastName(String lastName) {
        this.lastName = lastName;
        return this;
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

    public AppUserDetails setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
