package bg.tihomir.hobby.web;

import bg.tihomir.hobby.model.dto.binding.BusinessRegisterDTO;
import bg.tihomir.hobby.model.dto.binding.BusinessUserUpdateDTO;
import bg.tihomir.hobby.model.dto.binding.UserRegisterDTO;
import bg.tihomir.hobby.model.dto.view.BusinessUserView;
import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import bg.tihomir.hobby.model.user.AppUserDetails;
import bg.tihomir.hobby.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;
    private final ModelMapper modelMapper;

    public UserController(UserService userService,
                          SecurityContextRepository securityContextRepository,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }

    @PostMapping("/login-error")
    public ModelAndView onFailure(@ModelAttribute("email") String email, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("email", email);

        modelAndView.setViewName("auth-login");

        return modelAndView;

/*        redirectAttributes
                .addAttribute("email", email)
                .addAttribute("bad_credentials", true);

        return "auth-login";*/
    }

    @ModelAttribute("registerDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
            model.addAttribute("isExists", false);
            model.addAttribute("dontMatch", false);
        }
        return "auth-register";
    }

    @PostMapping("/register")
    public String confirmRegister(@Valid UserRegisterDTO registerDTO,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/users/register";
        }

        this.userService.registerUser(registerDTO, authentication -> {
            // populating security context
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(authentication);
            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }

    @ModelAttribute("businessRegDTO")
    public BusinessRegisterDTO businessRegDTO() {
        return new BusinessRegisterDTO();
    }

    @GetMapping("/register-business")
    public String registerBusiness() {
        return "auth-register-business";
    }

    @PostMapping("/register-business")
    public String confirmRegisterBusiness(@Valid BusinessRegisterDTO businessRegDTO,
                                          BindingResult bindingResult,
                                          HttpServletRequest request,
                                          HttpServletResponse response,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("businessRegDTO",
                    businessRegDTO).addFlashAttribute("org.springframework.validation.BindingResult.businessRegDTO", bindingResult);
            return "redirect:/users/register-business";
        }


        this.userService.registerBusinessUser(businessRegDTO, authentication -> {
            // populating security context
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(authentication);
            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        return "redirect:/";
    }

    @GetMapping("/business-account/{id}")
    public String getAccount(@PathVariable("id") Long id, Model model) {

        BusinessUserUpdateDTO updatedBusinessDTO = this.userService.findCurrentUserBusinessOwnerById(id);

        if (!model.containsAttribute("account")) {
            model.addAttribute("account", updatedBusinessDTO);
        }

        return "business-account";
    }

    @GetMapping("/update-business-user/{id}")
    public String updateBusinessUser(Model model, @PathVariable("id") Long id) {

        BusinessUserUpdateDTO updatedBusinessDTO = this.userService.findCurrentUserBusinessOwnerById(id);

        if (!model.containsAttribute("updatedBusinessDTO")) {
            model.addAttribute("updatedBusinessDTO", updatedBusinessDTO);
        }
        return "business-account-update";
    }

    @PostMapping("/update-business-user/{id}")
    public String confirmUpdateBusinessUser(@PathVariable("id") Long id,
                                            @Validated @ModelAttribute("updatedBusinessDTO") BusinessUserUpdateDTO updatedBusinessDTO,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("updatedBusinessDTO", updatedBusinessDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updatedBusinessDTO", bindingResult);

            return "redirect:/users/update-business-user/" + id;
        }

        BusinessUserUpdateDTO userBeforeUpdate = this.userService.findCurrentUserBusinessOwnerById(id);

        // Fetch the existing user data from the database
        BusinessUserView userAfterUpdate = this.userService.updateBusinessUser(updatedBusinessDTO);

        // Create a list to store all the messages
        List<String> messages = new ArrayList<>();


        // Check for differences between the existing data and the updated data
        if (!userAfterUpdate.getFirstName().equals(userBeforeUpdate.getFirstName())) {
            messages.add("The first name is changed to: " + userAfterUpdate.getFirstName());
        }
        if (!userAfterUpdate.getLastName().equals(userBeforeUpdate.getLastName())) {
            messages.add("The last name changed to: " + userAfterUpdate.getLastName());
        }
        if (!userAfterUpdate.getUsername().equals(userBeforeUpdate.getUsername())) {
            messages.add("The username changed to: " + userAfterUpdate.getUsername());
        }
        if (!userAfterUpdate.getEmail().equals(userBeforeUpdate.getEmail())) {
            messages.add("The email changed to: " + userAfterUpdate.getEmail());
        }

        // Add the list of messages to the redirect attributes
        redirectAttributes.addFlashAttribute("messages", messages);


        return "redirect:/users/update-business-user/" + id;
    }



    @GetMapping("/account/{id}")
    private String getUserAccount(@PathVariable("id") Long id, Model model,
                                  Authentication authentication) {

        UserRegisterDTO userRegisterDTO = userService.findCurrentUserById(id);

        if (!model.containsAttribute("account")) {
            model.addAttribute("account", userRegisterDTO);
        }

        return "user-account";
    }

}
