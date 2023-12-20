package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/manageUserRoles")
    public void manageUserRoles(@RequestBody String model){
        this.userService.updateUserRoles(model);
    }
}
