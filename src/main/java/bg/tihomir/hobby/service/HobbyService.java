package bg.tihomir.hobby.service;

import bg.tihomir.hobby.handler.NoHobbiesFoundException;
import bg.tihomir.hobby.handler.NotFoundException;
import bg.tihomir.hobby.model.dto.binding.HobbyOfferDTO;
import bg.tihomir.hobby.model.dto.binding.TestDTO;
import bg.tihomir.hobby.model.dto.binding.UpdateHobbyOfferDTO;
import bg.tihomir.hobby.model.dto.view.HobbyOfferView;
import bg.tihomir.hobby.model.dto.view.SearchHobbyView;
import bg.tihomir.hobby.model.entity.AppClient;
import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import bg.tihomir.hobby.model.entity.HobbyEntity;
import bg.tihomir.hobby.model.entity.LocationEntity;
import bg.tihomir.hobby.model.enums.CategoryNameEnum;
import bg.tihomir.hobby.model.enums.LocationEnum;
import bg.tihomir.hobby.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HobbyService {

    private final CategoryService categoryService;
    private final UserService userService;
    private final LocationService locationService;
    private final LocationRepository locationRepository;
    private final CloudinaryService cloudinaryService;
    private final HobbyRepository hobbyRepository;
    private final ModelMapper modelMapper;
    private final AppClientRepository appClientRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AboService aboService;
    private final ShoppingCartService shoppingCartService;

    public HobbyService(CategoryService categoryService,
                        UserService userService,
                        LocationService locationService,
                        LocationRepository locationRepository,
                        CloudinaryService cloudinaryService,
                        HobbyRepository hobbyRepository,
                        ModelMapper modelMapper,
                        AppClientRepository appClientRepository,
                        UserRepository userRepository,
                        CategoryRepository categoryRepository,
                        AboService aboService,
                        ShoppingCartService shoppingCartService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.locationService = locationService;
        this.locationRepository = locationRepository;
        this.cloudinaryService = cloudinaryService;
        this.hobbyRepository = hobbyRepository;
        this.modelMapper = modelMapper;
        this.appClientRepository = appClientRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.aboService = aboService;
        this.shoppingCartService = shoppingCartService;
    }

    public Long createHobbyWithImages(HobbyOfferDTO hobbyOfferDTO,
                                      List<String> mainImageUrls,
                                      List<String> imgOneUrls,
                                      List<String> imgTwoUrls,
                                      List<String> imgThreeUrls) {
        HobbyEntity hobbyEntity = new HobbyEntity();
        hobbyEntity.setName(hobbyOfferDTO.getName());
        hobbyEntity.setDescription(hobbyOfferDTO.getDescription());
        hobbyEntity.setPrice(hobbyOfferDTO.getPrice());
        hobbyEntity.setCategory(this.categoryService.findByName(hobbyOfferDTO.getCategory()));
        hobbyEntity.setBusinessOwner(this.userService.findCurrentUserBusinessOwner());
        hobbyEntity.setLocation(this.locationService.getLocationByName(hobbyOfferDTO.getLocation()));

        // Задаване на URL адресите на изображенията
        hobbyEntity.setImgUrlMain(mainImageUrls.get(0));
        hobbyEntity.setImgUrlOne(imgOneUrls.isEmpty() ? null : imgOneUrls.get(0));
        hobbyEntity.setImgUrlTwo(imgTwoUrls.isEmpty() ? null : imgTwoUrls.get(0));
        hobbyEntity.setImgUrlThree(imgThreeUrls.isEmpty() ? null : imgThreeUrls.get(0));
        // Съхранение на хобито в базата данни
        this.hobbyRepository.save(hobbyEntity);
        return hobbyEntity.getId();
    }

    public void updateHobbyWithImages(Long hobbyId,
                                      UpdateHobbyOfferDTO dto,
                                      List<String> mainImageUrls,
                                      List<String> imgOneUrls,
                                      List<String> imgTwoUrls,
                                      List<String> imgThreeUrls) {

        HobbyEntity hobbyEntity = hobbyRepository.findById(hobbyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hobby Id: " + hobbyId));

        hobbyEntity.setName(dto.getName());
        hobbyEntity.setDescription(dto.getDescription());
        hobbyEntity.setCategory(categoryService.findByName(dto.getCategory()));
        hobbyEntity.setPrice(dto.getPrice());
        hobbyEntity.setLocation(locationService.getLocationByName(dto.getLocation()));

        // Set the image URLs if they are provided
        if (!mainImageUrls.isEmpty()) {
            hobbyEntity.setImgUrlMain(mainImageUrls.get(0));
        }
        if (!imgOneUrls.isEmpty()) {
            hobbyEntity.setImgUrlOne(imgOneUrls.get(0));
        }
        if (!imgTwoUrls.isEmpty()) {
            hobbyEntity.setImgUrlTwo(imgTwoUrls.get(0));
        }
        if (!imgThreeUrls.isEmpty()) {
            hobbyEntity.setImgUrlThree(imgThreeUrls.get(0));
        }

        // Save the changes to the database
        hobbyRepository.save(hobbyEntity);
    }

    public List<HobbyOfferView> findAllHobbies() {

        return this.hobbyRepository.findAll()
                .stream()
                .map(hobbyEntity -> {
                    HobbyOfferView hobbyOfferView = modelMapper.map(hobbyEntity, HobbyOfferView.class);
                    hobbyOfferView.setCategory(hobbyEntity.getCategory().getName());
                    hobbyOfferView.setLocation(hobbyEntity.getLocation().getName());
                    return hobbyOfferView;
                })
                .collect(Collectors.toList());

    }

    public Optional<HobbyOfferView> findById(Long id) {
        return hobbyRepository
                .findById(id)
                .map(hobbyEntity -> {
                    HobbyOfferView hobbyOfferView = modelMapper.map(hobbyEntity, HobbyOfferView.class);
                    hobbyOfferView.setCategory(hobbyEntity.getCategory().getName());
                    hobbyOfferView.setLocation(hobbyEntity.getLocation().getName());
                    return hobbyOfferView;
                });
    }

    public List<HobbyOfferView> findAllByBusinessOwner(BusinessOwnerEntity businessOwner) {

        return this.hobbyRepository
                .findAllByBusinessOwner(businessOwner)
                .stream()
                .map(hobbyEntity -> {
                    HobbyOfferView hobbyOfferView = modelMapper.map(hobbyEntity, HobbyOfferView.class);
                    hobbyOfferView.setCategory(hobbyEntity.getCategory().getName());
                    hobbyOfferView.setLocation(hobbyEntity.getLocation().getName());
                    return hobbyOfferView;
                })
                .collect(Collectors.toList());
    }

    public HobbyEntity findHobbyById(Long id) {
        return this.hobbyRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("This hobby does not exists"));
    }

    public void saveHobbyForClient(HobbyEntity hobby) {
        AppClient currentUserAppClient = this.userService.findCurrentUserAppClient();

        Optional<HobbyEntity> hobbyById = this.hobbyRepository.findById(hobby.getId());
        List<HobbyEntity> saved_hobbies = currentUserAppClient.getSaved_hobbies();

        if (hobbyById.isPresent() && !(saved_hobbies.contains(hobbyById.get()))) {
            saved_hobbies.add(hobbyById.get());
            this.userRepository.save(currentUserAppClient);
        }
    }

    public boolean isHobbySaved(Long hobbyId) {
        AppClient currentUserAppClient = this.userService.findCurrentUserAppClient();
        Optional<HobbyEntity> byId = this.hobbyRepository.findById(hobbyId);
        return byId.filter(hobby ->
                currentUserAppClient.getSaved_hobbies().contains(hobby)).isPresent();
    }

    public void removeHobbyFromClient(HobbyEntity hobby) {
        AppClient currentUserAppClient = this.userService.findCurrentUserAppClient();
        Optional<HobbyEntity> hobbyById = this.hobbyRepository.findById(hobby.getId());
        hobbyById.ifPresent(value -> currentUserAppClient.getSaved_hobbies().remove(value));
        this.userRepository.save(currentUserAppClient);
    }

    public List<HobbyOfferView> findRandomHobbies(TestDTO testDTO) {
        List<HobbyEntity> hobby_matches = new ArrayList<>();
        Random rand = new Random();
        LocationEnum location = testDTO.getLocation();
        LocationEntity locationByName = this.locationService.getLocationByName(location);
        List<HobbyEntity> allByLocation = this.hobbyRepository.findAllByLocation(locationByName);
        List<CategoryNameEnum> testCategoryResults = Arrays.asList(
                testDTO.getCategoryOne(),
                testDTO.getCategoryTwo(),
                testDTO.getCategoryThree(),
                testDTO.getCategoryFour(),
                testDTO.getCategoryFive(),
                testDTO.getCategorySix()
        );

        if (allByLocation.isEmpty()) {
            throw new NoHobbiesFoundException
                    ("No hobbies found for the selected location!");
        }

        List<HobbyEntity> matchedHobbies = new ArrayList<>();

        for (HobbyEntity hobby : allByLocation) {
            for (CategoryNameEnum testCategory : testCategoryResults) {
                if (hobby.getCategory().getName().equals(testCategory)) {
                    matchedHobbies.add(hobby);
                    break;
                }
            }
        }

        if (!matchedHobbies.isEmpty()) {
            int limit = Math.min(3, matchedHobbies.size());
            while (hobby_matches.size() < limit) {
                int randomIndex = rand.nextInt(matchedHobbies.size());
                HobbyEntity randomHobby = matchedHobbies.get(randomIndex);
                if (!hobby_matches.contains(randomHobby)) {
                    hobby_matches.add(randomHobby);
                }
            }
        }

        // Map hobby_matches to hobbyOfferViews
        List<HobbyOfferView> hobbyOfferViews = hobby_matches.stream()
                .map(hobby -> {
                    HobbyOfferView dto = modelMapper.map(hobby, HobbyOfferView.class);
                    dto.setCategory(hobby.getCategory().getName());
                    dto.setLocation(hobby.getLocation().getName());
                    return dto;
                })
                .collect(Collectors.toList());

        return hobbyOfferViews;
    }

    public List<HobbyEntity> findSavedHobbies(AppClient currentUserAppClient) {
        return currentUserAppClient.getSaved_hobbies();
    }

    public Page<HobbyOfferView> searchHobbies(SearchHobbyView searchHobbyView,
                                              Pageable pageable) {
        return hobbyRepository
                .findAll(new HobbySpecification(searchHobbyView), pageable)
                .map(hobby -> {
                    HobbyOfferView hobbyOfferView = modelMapper.map(hobby, HobbyOfferView.class);
                    hobbyOfferView.setCategory(hobby.getCategory().getName());
                    hobbyOfferView.setLocation(hobby.getLocation().getName());
                    return hobbyOfferView;
                });
    }

    public void deleteHobbyOffer(Long id) {
        Optional<HobbyEntity> byId = this.hobbyRepository.findById(id);
        if (byId.isPresent()) {
            this.userService.findAndRemoveHobbyFromClientsRecords(byId.get());
            this.aboService.findExcistingAbosWithHobbyId(id);
            this.shoppingCartService.removeProductFromCart(id);
            this.hobbyRepository.deleteById(id);
        } else {
            throw new NotFoundException("Hobby does not exist");
        }
    }

    public HobbyOfferView findHobbyOfferViewById(Long id) {

        return this.hobbyRepository.findById(id).map(hobbyEntity -> {
                    HobbyOfferView hobbyOffer = modelMapper.map(hobbyEntity, HobbyOfferView.class);
                    hobbyOffer.setCategory(hobbyEntity.getCategory().getName());
                    hobbyOffer.setLocation(hobbyEntity.getLocation().getName());
                    hobbyOffer.setImgUrlMain(hobbyEntity.getImgUrlMain());
                    return hobbyOffer;
                }).orElseThrow(() ->  new IllegalArgumentException("Hobby with id " + id + " not found"));
    }
}
