package org.softuni.pastryShop.web;

import org.softuni.pastryShop.model.dto.UserDTO;
import org.softuni.pastryShop.model.dto.UserEditRolesDTOHolder;
import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.service.RoleService;
import org.softuni.pastryShop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/changeRole")
    public ModelAndView role(Model model){
        model.addAttribute("users",userService.getAll());
        return new ModelAndView("/changeRole");
    }


    @GetMapping("/manageUserRoles")
    public ModelAndView manageUserRoles(Model model, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        UserDTO user = userService.getUserDTOByEmail(userDetails.getUsername());
        UserEditRolesDTOHolder usersHolder = new UserEditRolesDTOHolder();
        usersHolder.setUsers(userService.getUserEditRolesDTOs());
        List<Role> roles = roleService.getAllRoles();

        if (!model.containsAttribute("userDTO")) {
            model.addAttribute("userDTO", user);
        }

        if (!model.containsAttribute("usersHolder")) {
            model.addAttribute("usersHolder", usersHolder);
        }

        if (!model.containsAttribute("allRoles")) {
            model.addAttribute("allRoles", roles);
        }

        return new ModelAndView("manageUserRoles");
    }
}
