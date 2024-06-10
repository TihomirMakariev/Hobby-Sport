package bg.tihomir.hobby.web;

import bg.tihomir.hobby.model.dto.binding.TestDTO;
import bg.tihomir.hobby.model.dto.view.HobbyOfferView;
import bg.tihomir.hobby.service.HobbyService;
import bg.tihomir.hobby.service.TestService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TestController {

    private final TestService testService;
    private final ModelMapper modelMapper;
    private final HobbyService hobbyService;

    public TestController(TestService testService,
                          ModelMapper modelMapper,
                          HobbyService hobbyService) {
        this.testService = testService;
        this.modelMapper = modelMapper;
        this.hobbyService = hobbyService;
    }

    @GetMapping("/test")
    public String showTest(Model model) {
        if (!model.containsAttribute("testDTO")) {
            model.addAttribute("testDTO", new TestDTO());
        }
        return "test/test";
    }

    @PostMapping("/test")
    public String saveTestResult(@Valid TestDTO testDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("testDTO", testDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.testDTO", bindingResult);
            return "redirect:/test";
        } else {
            List<HobbyOfferView> randomHobbies = hobbyService.findRandomHobbies(testDTO);
            model.addAttribute("randomHobbies", randomHobbies);
            return "hobby-test-random";  // Ensure this matches the correct path of your template
        }
    }
}