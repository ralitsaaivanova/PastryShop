package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
