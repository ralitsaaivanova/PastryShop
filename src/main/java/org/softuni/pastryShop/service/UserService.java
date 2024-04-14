package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.UserDTO;
import org.softuni.pastryShop.model.dto.UserEditRolesDTO;
import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.model.entities.User;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void createUserIfNotExist(String email, String names);

    Authentication login(String email);

    List<UserDTO> getAll();


    void updateUsersRoles(List<UserEditRolesDTO> usersEdit);

    void updateUserRoles(String json);

    UserDTO getUserDTOByEmail(String email) throws IOException;

    List<UserEditRolesDTO> getUserEditRolesDTOs();
}
