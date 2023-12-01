package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLogController {

    private final UserService userService;

    public UserLogController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/signin")
    public String login(){
        return "signin";
    }
}
