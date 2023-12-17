package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void createUserIfNotExist(String email, String names);

    Authentication login(String email);

}
