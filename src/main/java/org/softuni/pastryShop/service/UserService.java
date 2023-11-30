package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    Authentication login(String email);
}
