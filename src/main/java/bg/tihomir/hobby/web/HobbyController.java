package bg.tihomir.hobby.web;

import bg.tihomir.hobby.model.dto.binding.HobbyOfferDTO;
import bg.tihomir.hobby.model.dto.binding.UpdateHobbyOfferDTO;
import bg.tihomir.hobby.model.dto.view.AppClientView;
import bg.tihomir.hobby.model.dto.view.HobbyOfferView;
import bg.tihomir.hobby.model.dto.view.SearchHobbyView;
import bg.tihomir.hobby.model.entity.HobbyEntity;
import bg.tihomir.hobby.model.user.AppUserDetails;
import bg.tihomir.hobby.repository.HobbyRepository;
import bg.tihomir.hobby.service.BusinessOwnerService;
import bg.tihomir.hobby.service.CloudinaryService;
import bg.tihomir.hobby.service.HobbyService;
import bg.tihomir.hobby.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hobbies")
public class HobbyController {

    private final HobbyService hobbyService;
    private final BusinessOwnerService businessOwnerService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final HobbyRepository hobbyRepository;
    private final CloudinaryService cloudinaryService;

    public HobbyController(HobbyService hobbyService,
                           BusinessOwnerService businessOwnerService,
                           ModelMapper modelMapper,
                           UserService userService,
                           HobbyRepository hobbyRepository,
                           CloudinaryService cloudinaryService) {
        this.hobbyService = hobbyService;
        this.businessOwnerService = businessOwnerService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.hobbyRepository = hobbyRepository;
        this.cloudinaryService = cloudinaryService;
    }


    @ModelAttribute("appClientView")
    public AppClientView appClientView() {
        return new AppClientView();
    }
    @ModelAttribute("hobbyOfferView")
    public HobbyOfferView hobbyOfferView() {
        return new HobbyOfferView();
    }
    @ModelAttribute("hobbyOfferDTO")
    public HobbyOfferDTO hobbyOfferDTO() {
        return new HobbyOfferDTO();
    }

    @GetMapping("/all")
    public String allHobbies(SearchHobbyView searchHobbyView,
                             Model model,
                             @PageableDefault(page = 0, size = 3, sort = "name", direction = Sort.Direction.ASC)
                             Pageable pageable)


    {

        if (!model.containsAttribute("searchHobbies")) {
            model.addAttribute("searchHobbies", new SearchHobbyView());
        }

        Page<HobbyOfferView> filteredHobbies =
                this.hobbyService.searchHobbies(searchHobbyView, pageable);


        model.addAttribute("hobbies", filteredHobbies);
        model.addAttribute("searchHobbies", searchHobbyView);

        return "hobby";
    }

    @GetMapping("/create-offer")
    public String createOffer() {
        return "hobby-create-offer";
    }

    @PostMapping("/create-offer")
    public String confirmCreateOffer(@Valid HobbyOfferDTO hobbyOfferDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     @RequestParam("imgMain") List<MultipartFile> imgMainFiles,
                                     @RequestParam("imgOne") List<MultipartFile> imgOneFiles,
                                     @RequestParam("imgTwo") List<MultipartFile> imgTwoFiles,
                                     @RequestParam("imgThree") List<MultipartFile> imgThreeFiles) throws IOException {

        // Проверка за грешки във входните данни
        if (bindingResult.hasFieldErrors()) {
            redirectAttributes
                    .addFlashAttribute("hobbyOfferDTO", hobbyOfferDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.hobbyOfferDTO", bindingResult);
            return "redirect:/hobbies/create-offer";
        }

        // Качване на главните изображения
        List<String> mainImageUrls = uploadImagesToCloudinary(imgMainFiles);
        // Качване на изображенията за първи план
        List<String> imgOneUrls = uploadImagesToCloudinary(imgOneFiles);
        // Качване на изображенията за втори план
        List<String> imgTwoUrls = uploadImagesToCloudinary(imgTwoFiles);
        // Качване на изображенията за трети план
        List<String> imgThreeUrls = uploadImagesToCloudinary(imgThreeFiles);

        // Създаване на хобито с URL адресите на изображенията
        this.hobbyService.createHobbyWithImages(hobbyOfferDTO, mainImageUrls, imgOneUrls, imgTwoUrls, imgThreeUrls);

        return "redirect:/hobbies/all";
    }

    @GetMapping("/update-offer/{id}")
    public String updateHobbyOffer(@PathVariable("id") Long id, Model model) {

        HobbyOfferView hobbyOfferView = this.hobbyService.findHobbyOfferViewById(id);
        UpdateHobbyOfferDTO updateHobbyOfferDTO = modelMapper.map(hobbyOfferView, UpdateHobbyOfferDTO.class);

        model.addAttribute("updateHobbyOfferDTO", updateHobbyOfferDTO);

        return "hobby-update-offer";
    }

    @PostMapping("/update-offer/{id}")
    public String updateHobbyOffer(@PathVariable("id") Long hobbyId,
                                   @Valid UpdateHobbyOfferDTO updateHobby,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("imgUrlMain") List<MultipartFile> imgUrlMain,
                                   @RequestParam("imgUrlOne") List<MultipartFile> imgUrlOne,
                                   @RequestParam("imgUrlTwo") List<MultipartFile> imgUrlTwo,
                                   @RequestParam("imgUrlThree") List<MultipartFile> imgUrlThree) throws IOException {

        // Проверка за грешки във входните данни
        if (bindingResult.hasFieldErrors()) {
            redirectAttributes
                    .addFlashAttribute("hobbyOfferDTO", updateHobby)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updateHobby", bindingResult);
            return "redirect:/hobbies/update-offer/" + hobbyId;
        }

        // Качване на главните изображения
        List<String> mainImageUrls = imgUrlMain.isEmpty() ? Collections.emptyList() : uploadImagesToCloudinary(imgUrlMain);
        // Качване на изображенията за първи план
        List<String> imgOneUrls = imgUrlOne.isEmpty() ? Collections.emptyList() : uploadImagesToCloudinary(imgUrlOne);
        // Качване на изображенията за втори план
        List<String> imgTwoUrls = imgUrlTwo.isEmpty() ? Collections.emptyList() : uploadImagesToCloudinary(imgUrlTwo);
        // Качване на изображенията за трети план
        List<String> imgThreeUrls = imgUrlThree.isEmpty() ? Collections.emptyList() : uploadImagesToCloudinary(imgUrlThree);

        // Обновяване на хобито с новите данни и URL адресите на изображенията
        this.hobbyService.updateHobbyWithImages(hobbyId, updateHobby, mainImageUrls, imgOneUrls, imgTwoUrls, imgThreeUrls);

        return "redirect:/hobbies/all";
    }

    @GetMapping("/details/{id}")
    public String hobbyDetails(@PathVariable("id") Long id, Model model,
                               Authentication authentication, Principal principal) {

        HobbyEntity hobby = hobbyService.findHobbyById(id); // или каквато и да е логиката ви за зареждане на хоби
        String username = principal != null ? principal.getName() : null;
        boolean isOwnerOrAdmin = false;

        // Проверка дали потребителят е логнат
        AppUserDetails userDetails = null;
        Long userId = null;
        boolean hasBusinessRole = false;
        boolean isSaved = false;

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (AppUserDetails) authentication.getPrincipal();
            userId = userDetails.getId();
            hasBusinessRole = this.userService.checkIfUserHasBusinessRole(userId);
        }

        Optional<HobbyOfferView> hobbyOfferViewOptional = this.hobbyService.findById(id);

        if (hobbyOfferViewOptional.isPresent()) {
            HobbyOfferView hobbyOfferView = hobbyOfferViewOptional.get();
            model.addAttribute("hobbyOfferView", hobbyOfferView);

            if (userDetails == null) {
                model.addAttribute("registrationMessage", "To add this hobby you need to ");

                return "hobby-details"; // Потребителят не е логнат, връщаме "hobby-details" с допълнителния атрибут

            } else if (hasBusinessRole) {
                isOwnerOrAdmin = userService.isUserAllowedToDeleteHobbyOffer(id, username);
                model.addAttribute("hobby", hobby);
                model.addAttribute("isOwnerOrAdmin", isOwnerOrAdmin);

                return "hobby-details";
            } else {
                // Добавяне на атрибут за състояние на запазване на хобито, ако потребителят няма бизнес роля
                isSaved = hobbyService.isHobbySaved(id);
                model.addAttribute("isSaved", isSaved);
                return "hobby-details";
            }
        } else {
            return "error-page";
        }
    }

    @GetMapping("/save-hobby/{id}")
    public String saveHobbyToHobbyList(@PathVariable("id") Long id, Model model) {

        HobbyEntity hobby = this.hobbyService.findHobbyById(id);
        this.hobbyService.saveHobbyForClient(hobby);

        HobbyOfferView hobbyOfferView = modelMapper.map(hobby, HobbyOfferView.class);
        hobbyOfferView.setCategory(hobby.getCategory().getName());
        hobbyOfferView.setLocation(hobby.getLocation().getName());
        model.addAttribute("hobbyOfferView", hobbyOfferView);
        model.addAttribute("isSaved", this.hobbyService.isHobbySaved(id));

        return "redirect:/hobbies/all";
    }

    @GetMapping("/remove-hobby/{id}")
    private String removeHobbyFromHobbyList(@PathVariable("id") Long id,
                                            Model model) {
        HobbyEntity hobby = this.hobbyService.findHobbyById(id);
        this.hobbyService.removeHobbyFromClient(hobby);
        model.addAttribute("hobbyOfferView", hobby);
        model.addAttribute("isSaved", this.hobbyService.isHobbySaved(id));
        return "hobby-details";
    }

    @GetMapping("/my-hobbies")
    public ModelAndView getMyHobbies(Model model) {
        ModelAndView modelAndView = new ModelAndView("hobby-my-hobbies");
        /*AppClient currentAppClient = this.userService.findCurrentUserAppClient();*/
        AppClientView currentAppClient = this.userService.findCurrentUserAppClientView();
        List<HobbyEntity> hobbies = currentAppClient.getSaved_hobbies();
        modelAndView.addObject("savedHobbies", hobbies);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteHobbyOffer(@PathVariable("id") Long id, Principal principal,
                                   RedirectAttributes redirectAttributes) {

        // Get the current user's name
        String username = principal.getName();

        boolean isUserCanDelete = this.userService.isUserAllowedToDeleteHobbyOffer(id, username);

        // Check if the current user is authorized to delete the hobby offer
        if (isUserCanDelete) {
            // User is authorized, proceed with deletion
            this.hobbyService.deleteHobbyOffer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Hobby successfully deleted.");
            return "redirect:/hobbies/all";
        } else {
            // User is not authorized, redirect to error page
            return "redirect:/error/unauthorized";
        }
    }

    private List<String> uploadImagesToCloudinary(List<MultipartFile> files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(file);
                imageUrls.add(imageUrl);
            }
        }
        return imageUrls;
    }
}