package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

    }
}
