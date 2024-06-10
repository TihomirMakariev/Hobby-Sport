package bg.tihomir.hobby.web;

import bg.tihomir.hobby.service.HobbyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    private final HobbyService hobbyService;

    public HomeController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }




    



}
