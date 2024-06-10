package bg.tihomir.hobby.service;

import bg.tihomir.hobby.handler.NotFoundException;
import bg.tihomir.hobby.model.dto.binding.BusinessRegisterDTO;
import bg.tihomir.hobby.model.dto.binding.BusinessUserUpdateDTO;
import bg.tihomir.hobby.model.dto.binding.UserRegisterDTO;
import bg.tihomir.hobby.model.dto.view.AppClientView;
import bg.tihomir.hobby.model.dto.view.BusinessUserView;
import bg.tihomir.hobby.model.entity.*;
import bg.tihomir.hobby.model.enums.UserRoleEnum;
import bg.tihomir.hobby.repository.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final AppClientRepository appClientRepository;
    private final HobbyRepository hobbyRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService,
                       ModelMapper modelMapper,
                       UserRoleRepository userRoleRepository,
                       BusinessOwnerRepository businessOwnerRepository,
                       AppClientRepository appClientRepository,
                       HobbyRepository hobbyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;

        this.businessOwnerRepository = businessOwnerRepository;
        this.appClientRepository = appClientRepository;
        this.hobbyRepository = hobbyRepository;
    }


    public void registerUser(UserRegisterDTO registerDTO,
                             Consumer<Authentication> successfulLoginProcessor) {
        UserRoleEntity userRole = userRoleRepository.findByUserRole(UserRoleEnum.valueOf("USER"));

        AppClient newAppClient = new AppClient();
        newAppClient.setFirstName(registerDTO.getFirstName());
        newAppClient.setLastName(registerDTO.getLastName());
        newAppClient.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        newAppClient.setUsername(registerDTO.getUsername());
        newAppClient.setEmail(registerDTO.getEmail());
        newAppClient.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newAppClient.setActive(true);
        newAppClient.setImageUrl(registerDTO.getImageUrl());
        newAppClient.setRoles(List.of(userRole));

        this.appClientRepository.save(newAppClient);

        UserDetails userDetails = userDetailsService.loadUserByUsername(registerDTO.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);

    }

    public void registerBusinessUser(BusinessRegisterDTO businessRegDTO,
                                     Consumer<Authentication> successfulLoginProcessor) {
        UserRoleEntity businessUserRoles = userRoleRepository
                .findByUserRole(UserRoleEnum.valueOf("BUSINESS_USER"));

        BusinessOwnerEntity newBusinessOwner = new BusinessOwnerEntity();
        newBusinessOwner.setUsername(businessRegDTO.getUsername());
        newBusinessOwner.setFirstName(businessRegDTO.getFirstName());
        newBusinessOwner.setLastName(businessRegDTO.getLastName());
        newBusinessOwner.setEmail(businessRegDTO.getEmail());
        newBusinessOwner.setPassword(passwordEncoder.encode(businessRegDTO.getPassword()));
        newBusinessOwner.setImageUrl(businessRegDTO.getImageUrl());
        newBusinessOwner.setBusinessName(businessRegDTO.getBusinessName());
        newBusinessOwner.setAddress(businessRegDTO.getAddress());
        newBusinessOwner.setRoles(List.of(businessUserRoles));

        userRepository.save(newBusinessOwner);

        UserDetails userDetails = userDetailsService.loadUserByUsername(businessRegDTO.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        successfulLoginProcessor.accept(authentication);
    }

    public BusinessOwnerEntity findCurrentUserBusinessOwner() {
        Optional<BusinessOwnerEntity> user =
                this.businessOwnerRepository.findByEmail(findCurrentUsername());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("Can not find current business owner");
        }
    }

    public AppClient findCurrentUserAppClient() {
        Optional<AppClient> user = this.appClientRepository.findByEmail(findCurrentUsername());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("Can not find current user");
        }
    }

    public String findCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public AppClientView findCurrentUserAppClientView() {
        Optional<AppClient> userOptional = this.appClientRepository.findByEmail(findCurrentUsername());
        if (userOptional.isPresent()) {
            AppClient user = userOptional.get();
            return modelMapper.map(user, AppClientView.class);
        } else {
            throw new NotFoundException("Can not find current user");
        }
    }

    public boolean checkIfUserHasBusinessRole(Long id) {
        UserEntity user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This role does not exists"));
        return user.hasBusinessRole();
    }

    public void findAndRemoveHobbyFromClientsRecords(HobbyEntity hobbyEntity) {
        List<AppClient> all = this.appClientRepository.findAll();

        for (AppClient appClient : all) {
            appClient.getSaved_hobbies().remove(hobbyEntity);
            appClient.getHobby_matches().remove(hobbyEntity);
        }
    }

    public boolean isUserAllowedToDeleteHobbyOffer(Long hobbyOfferId, String username) {

        // Проверка дали потребителят е администратор
        if (isUserAdmin(username)) {
            return true; // Администраторът има право да изтрива офертата
        }

        // Проверка дали потребителят е собственик на офертата
        Optional<HobbyEntity> optionalHobbyEntity = hobbyRepository.findById(hobbyOfferId);

        if (optionalHobbyEntity.isPresent()) {
            HobbyEntity hobbyEntity = optionalHobbyEntity.get();

            // Проверка дали собственикът на офертата е потребителят
            return hobbyEntity.getBusinessOwner().getEmail().equals(username);
        }

        return false; // Връщаме false, ако офертата не съществува
    }

    public boolean isUserAdmin(String username) {

        // Проверяваме дали съществува потребител с подаденото потребителско име
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        // Ако потребителят не съществува, връщаме false
        if (optionalUser.isEmpty()) {
            return false;
        }

        // Вземаме потребителя от optional
        UserEntity userEntity = optionalUser.get();

        // Проверяваме дали потребителят има ролята "ADMIN"
        return userEntity.getRoles()
                .stream()
                .anyMatch(role -> role.getUserRole().name().equals("ADMIN"));
    }

    public BusinessUserUpdateDTO findCurrentUserBusinessOwnerById(Long id) {

        return this.businessOwnerRepository.findById(id)
                .map(businessOwner -> modelMapper.map(businessOwner, BusinessUserUpdateDTO.class))
                .orElseThrow(() ->
                        new NotFoundException("Can not find current business owner"));

    }

    public BusinessUserView updateBusinessUser(BusinessUserUpdateDTO updateDTO) {
        BusinessOwnerEntity businessOwner = this.businessOwnerRepository.findById(updateDTO.getId())
                .orElseThrow(() -> new ObjectNotFoundException(updateDTO.getId(),
                        "Потребител с id: " + updateDTO.getId() + " не е намерен!"));

        businessOwner.setFirstName(updateDTO.getFirstName());
        businessOwner.setLastName(updateDTO.getLastName());
        businessOwner.setUsername(updateDTO.getUsername());
        businessOwner.setBusinessName(updateDTO.getBusinessName());
        businessOwner.setEmail(updateDTO.getEmail());
        businessOwner.setAddress(updateDTO.getAddress());

        // Проверете дали паролата е празна или null, преди да я зададете
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            businessOwner.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        this.userRepository.save(businessOwner);

        return modelMapper.map(businessOwner, BusinessUserView.class);

    }

    public UserRegisterDTO findCurrentUserById(Long id) {
        return this.userRepository.findById(id)
                .map(userEntity -> modelMapper.map(userEntity, UserRegisterDTO.class))
                .orElseThrow(() -> new NotFoundException("Can not find current user"));
    }
}
