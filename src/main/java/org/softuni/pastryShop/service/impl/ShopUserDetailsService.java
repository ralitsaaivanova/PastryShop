package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.model.enums.RoleEnum;
import org.softuni.pastryShop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ShopUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .map(ShopUserDetailsService::map)
                .orElseThrow(()->new UsernameNotFoundException("User " + email + " not found!")) ;


        //        Optional<User> user = userRepository.findByEmail(email);
//        String password = user.get().getPassword();
//        String userRole = user.get().getRole().toString();
//
//
//        //check if user is found
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername(user.get().getEmail())
//                .password(user.get().getPassword())
//                .authorities(userRole(user.get().getRole()))//TODO: add roles
//                .build();
//
//        return userDetails;
    }

    private static GrantedAuthority map(Role roleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + roleEntity.getRole().name()
        );
    }

    private static UserDetails map(User userEntity) {
        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(ShopUserDetailsService::map).toList())
                .build();
    }

//    private GrantedAuthority userRole(RoleEnum role) {
//        return new SimpleGrantedAuthority(
//                "ROLE_" + role.name());
//    }

}