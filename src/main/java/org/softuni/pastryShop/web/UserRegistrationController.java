package org.softuni.pastryShop.web;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {

    public UserRegistrationController(UserService userService) {
    }

    @GetMapping("/signup")
    public String register(){
        return "signup";
    }


    @PostMapping("/signup")
    public ModelAndView signup(
            @ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO) {
//        if (loggedUser.isLogged()) {
//            return new ModelAndView("redirect:/index");
//        }

        return new ModelAndView("redirect:/signin");
    }

}