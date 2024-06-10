package bg.tihomir.hobby.web;

import bg.tihomir.hobby.model.entity.HobbyEntity;
import bg.tihomir.hobby.service.HobbyService;
import bg.tihomir.hobby.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {

    private final HobbyService hobbyService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(HobbyService hobbyService,
                                  ShoppingCartService shoppingCartService) {
        this.hobbyService = hobbyService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("shoppingCart");
        modelAndView.addObject("abos", this.shoppingCartService.getAbosInCart());
        modelAndView.addObject("total", this.shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{hobbyId}")
    public ModelAndView addProductToCart(@PathVariable("hobbyId") Long hobbyId) {
        HobbyEntity hobby = this.hobbyService.findHobbyById(hobbyId);
        this.shoppingCartService.addAboToCart(hobby);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{hobbyId}")
    public ModelAndView removeProductFromCart(@PathVariable("hobbyId") Long hobbyId) {
        this.shoppingCartService.removeProductFromCart(hobbyId);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public String checkout() {
        this.shoppingCartService.checkout();

        return "success";
    }



}
