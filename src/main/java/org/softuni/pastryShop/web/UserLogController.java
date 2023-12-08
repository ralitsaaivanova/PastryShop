package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLogController {

    private final UserService userService;

    public UserLogController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/signin")
    public ModelAndView login() {
        return new ModelAndView( "signin");
    }

    @PostMapping("/login-error")
    public ModelAndView onFailure(
            @ModelAttribute("email") String email,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return new ModelAndView("signin");
    }
}
