package org.softuni.pastryShop.web;

import org.springframework.web.bind.annotation.GetMapping;

public class UserLogController {
    @GetMapping("/signin")
    public String login(){
        return "signin";
    }
}
