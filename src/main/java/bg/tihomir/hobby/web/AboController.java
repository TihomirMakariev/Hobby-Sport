package bg.tihomir.hobby.web;

import bg.tihomir.hobby.service.AboService;
import bg.tihomir.hobby.service.EntryService;
import bg.tihomir.hobby.service.HobbyService;
import bg.tihomir.hobby.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboController {


    private final UserService userService;
    private final AboService aboService;
    private final HobbyService hobbyService;
    private final EntryService entryService;
    private final ModelMapper modelMapper;

    public AboController(UserService userService,
                         AboService aboService,
                         HobbyService hobbyService,
                         EntryService entryService,
                         ModelMapper modelMapper) {
        this.userService = userService;
        this.aboService = aboService;
        this.hobbyService = hobbyService;
        this.entryService = entryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my-abos")
    public ModelAndView showAllAbos() {
        ModelAndView modelAndView = new ModelAndView("abo/my-abos");
        modelAndView.addObject("saved", this.hobbyService.findSavedHobbies(this.userService.findCurrentUserAppClient()));
        modelAndView.addObject("abos", this.aboService.getUserAbos(this.userService.findCurrentUserAppClient().getId()));
        return modelAndView;
    }

    @GetMapping("delete-abo/{id}")
    public String deleteAbo(@PathVariable("id") Long id) {
        this.aboService.deleteAbo(id);
        return "redirect:/default";
    }




}
