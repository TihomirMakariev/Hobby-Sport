package bg.tihomir.hobby.web;


import bg.tihomir.hobby.model.dto.view.AboViewModel;
import bg.tihomir.hobby.model.dto.view.HobbyOfferView;
import bg.tihomir.hobby.model.entity.BusinessOwnerEntity;
import bg.tihomir.hobby.service.AboService;
import bg.tihomir.hobby.service.BusinessOwnerService;
import bg.tihomir.hobby.service.HobbyService;
import bg.tihomir.hobby.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/business")
public class BusinessController {


    private final UserService userService;
    private final HobbyService hobbyService;
    private final BusinessOwnerService businessOwnerService;
    private final AboService aboService;

    public BusinessController(UserService userService,
                              HobbyService hobbyService,
                              BusinessOwnerService businessOwnerService,
                              AboService aboService) {
        this.userService = userService;
        this.hobbyService = hobbyService;
        this.businessOwnerService = businessOwnerService;
        this.aboService = aboService;
    }

    @ModelAttribute("myOffers")
    public HobbyOfferView hobbyOfferView(){
        return new HobbyOfferView();
    }

    @GetMapping("/my-offers")
    public String getMyOffers(Authentication authentication, Model model) {

        String email = authentication.getName();

        BusinessOwnerEntity businessOwner = this.businessOwnerService.findByEmail(email);

        if (businessOwner != null) {
            List<HobbyOfferView> myOffers = this.hobbyService.findAllByBusinessOwner(businessOwner);
            if (!myOffers.isEmpty()) {
                model.addAttribute("myOffers", myOffers);
                model.addAttribute("businessOwner", true);
                return "business-my-offers";
            } else {
                model.addAttribute("message",
                        "There are currently no offers created.");
                return "business-no-offers-message";
            }
        }else {
            model.addAttribute("message", "Error: User not found.");
            return "error-message";
        }
    }

    @GetMapping("/my-clients")
    public String getMyClients(Authentication authentication, Model model) {

        String email = authentication.getName();
        BusinessOwnerEntity businessOwner = this.businessOwnerService.findByEmail(email);

        if (businessOwner != null) {
            List<AboViewModel> abos = this.aboService.getAbosPerBusiness();
            if (!abos.isEmpty()) {
                model.addAttribute("abos", abos);
                return "business-my-abos";
            } else {
                model.addAttribute("message", "There are currently no abos.");
                return "business-no-abos-message";
            }
        }else {
            model.addAttribute("message", "Error: User not found.");
            return "error-message";
        }
    }

}