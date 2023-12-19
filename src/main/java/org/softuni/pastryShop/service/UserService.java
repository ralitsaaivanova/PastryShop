package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.model.entities.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void createUserIfNotExist(String email, String names);

    Authentication login(String email);

    List<User> getAll();

}
