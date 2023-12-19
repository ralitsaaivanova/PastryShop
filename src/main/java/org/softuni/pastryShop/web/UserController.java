package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/changeRole")
    public ModelAndView role(Model model){
        model.addAttribute("users",userService.getAll());
        return new ModelAndView("/changeRole");
    }
}
