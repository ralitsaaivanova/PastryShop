package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.model.enums.Role;
import org.softuni.pastryShop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ShopUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        String password = user.get().getPassword();
        String userRole = user.get().getRole().toString();


        //check if user is found
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(userRole(user.get().getRole()))//TODO: add roles
                .build();

        return userDetails;
    }

    private GrantedAuthority userRole(Role role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.name());
    }

}