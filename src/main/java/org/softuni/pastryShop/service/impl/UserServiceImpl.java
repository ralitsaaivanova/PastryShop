package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.model.enums.Role;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));
    }

    @Override
    public Authentication login(String email) {
        return null;
    }

    private User map(UserRegistrationDTO userRegistrationDTO){
        String userRole = String.valueOf(userRegistrationDTO.role());
        return new User()
                .setEmail(userRegistrationDTO.email())
                .setUsername(userRegistrationDTO.username())
                .setRole(userRegistrationDTO.role())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
    }
}
