package bg.tihomir.hobby.service;

import bg.tihomir.hobby.model.entity.UserEntity;
import bg.tihomir.hobby.model.entity.UserRoleEntity;
import bg.tihomir.hobby.model.user.AppUserDetails;
import bg.tihomir.hobby.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;


public class AppUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findUserEntityByEmail(email)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private UserDetails map(UserEntity userEntity) {
        return new AppUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity
                        .getRoles()
                        .stream()
                        .map(this::mapRole).collect(Collectors.toList()))
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    private GrantedAuthority mapRole(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRoleEntity.getUserRole().name());
    }


}
