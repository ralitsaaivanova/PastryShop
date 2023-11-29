package org.softuni.pastryShop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UserLogController {
    @GetMapping("/signin")
    public String login(){
        return "signin";
    }
}
