package org.softuni.pastryShop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.softuni.pastryShop.model.dto.UserDTO;
import org.softuni.pastryShop.model.dto.UserDTOFromJson;
import org.softuni.pastryShop.model.dto.UserEditRolesDTO;
import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.model.dto.events.UserRegisteredEvent;
import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.model.enums.RoleEnum;
import org.softuni.pastryShop.repository.RoleRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService pastryShopUserDetailsService;
    private final ApplicationEventPublisher appEventPublisher;


    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher applicationEventPublisher,
                           UserDetailsService pastryShopUserDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher=applicationEventPublisher;
        this.pastryShopUserDetailsService = pastryShopUserDetailsService;
    }


    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));
        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.email(),
                userRegistrationDTO.username()
        ));
    }

    @Override
    public void updateUsersRoles(List<UserEditRolesDTO> usersEdit) {
        for (UserEditRolesDTO userEdit : usersEdit) {
            User user = userRepository.findById(userEdit.getId()).get();
            user.setRoles(userEdit.getRoles());
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserRoles(String json) {
        UserDTOFromJson[] data = null;

        try {
            data = new ObjectMapper().readValue(json, UserDTOFromJson[].class);
        } catch (Exception e) {
        }

        for (UserDTOFromJson userEdit : data) {
            User user = userRepository.findById(Long.parseLong(userEdit.id)).get();
            List<Role> roles = new ArrayList<>();
            for (String roleId :
                    userEdit.roles) {
                roles.add(this.roleRepository.findById(Long.parseLong(roleId)).get());
            }
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Override
    public UserDTO getUserDTOByEmail(String email) throws IOException {
        User user = userRepository.findByEmail(email).get();

        return mapUserToUserEditDTO(user);
    }

    @Override
    public List<UserEditRolesDTO> getUserEditRolesDTOs() {
        return userRepository.findAll().stream().map(this::mapUserToUserEditRolesDTO).toList();
    }



    @Override
    public void createUserIfNotExist(String email, String names) {
        // Create manually a user in the database
        // password not necessary
    }

    @Override
    public Authentication login(String email) {
        UserDetails userDetails = pastryShopUserDetailsService.loadUserByUsername(email);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    private User map(UserRegistrationDTO userRegistrationDTO) {
        return new User()
                .setEmail(userRegistrationDTO.email())
                .setUsername(userRegistrationDTO.username())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
    }


    private User mapUserDTOToUser(UserDTO userDTO) throws IOException {
        User user = new User()
                .setUsername(userDTO.getUsername())
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setRoles(userDTO.getRoles());

        user.setId(userDTO.getId());
        return user;
    }

    private UserDTO mapUserToUserEditDTO(User user) throws IOException {
        UserDTO userEditDTO = new UserDTO();

        userEditDTO.setId(user.getId());
        userEditDTO.setUsername(user.getUsername());
        userEditDTO.setEmail(user.getEmail());
        userEditDTO.setPassword(user.getPassword());
        userEditDTO.setRoles(user.getRoles());

        return userEditDTO;
    }


    private UserEditRolesDTO mapUserToUserEditRolesDTO(User user) {
        UserEditRolesDTO userEditRolesDTO = new UserEditRolesDTO();

        userEditRolesDTO.setId(user.getId());
        userEditRolesDTO.setUsername(user.getUsername());
        userEditRolesDTO.setRoles(user.getRoles());

        return userEditRolesDTO;
    }
}
