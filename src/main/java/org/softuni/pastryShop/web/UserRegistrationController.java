package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ModelAndView register() {
        return new ModelAndView("signup");
    }


    @PostMapping("/signup")
    public String signup(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {
        userService.registerUser(userRegistrationDTO);
//
//        if (!bindingResult.hasErrors()) {
//            userService.registerUser(userRegistrationDTO);
//            return "redirect:/signin";
//        }

        return "redirect:/signin";
    }

}
