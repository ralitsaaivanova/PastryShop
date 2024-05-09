package org.softuni.pastryShop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.softuni.pastryShop.model.dto.UserDTO;
import org.softuni.pastryShop.model.dto.UserDTOFromJson;
import org.softuni.pastryShop.model.dto.UserEditRolesDTO;
import org.softuni.pastryShop.model.dto.UserRegistrationDTO;
import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.RoleRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService pastryShopUserDetailsService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    public void testRegisterUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO("test@example.com", "testuser", "testpassword", "testpassword");

        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        userService.registerUser(registrationDTO);

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verify(applicationEventPublisher, times(1)).publishEvent(Mockito.any());
    }


    @Test
    void testGetUserDTOByEmail() {
        User user = new User();
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        try {
            UserDTO userDTO = userService.getUserDTOByEmail("test@test.com");
            assertEquals("test@test.com", userDTO.getEmail());
        } catch (Exception e) {
            assertTrue(false, "Exception should not be thrown");
        }
    }


    @Test
    void testLogin() {
        UserDetails userDetails = mock(UserDetails.class);
        when(pastryShopUserDetailsService.loadUserByUsername("test@test.com")).thenReturn(userDetails);

        assertEquals(userDetails, userService.login("test@test.com").getPrincipal());
    }

    @Test
    public void testGetUserEditRolesDTOs() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserEditRolesDTO> result = userService.getUserEditRolesDTOs();

        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
        assertEquals(user.getUsername(), result.get(0).getUsername());
    }

    @Test
    public void testUpdateUserRoles() {
        UserDTOFromJson userDTOFromJson = new UserDTOFromJson();
        userDTOFromJson.id = "1";
        userDTOFromJson.roles = List.of(new String[]{"1", "2"});

        String json = "[{\"id\":\"1\",\"roles\":[\"1\",\"2\"]}]";

        User existingUser = new User();
        existingUser.setId(1L);

        Role role1 = new Role();
        role1.setId(1L);

        Role role2 = new Role();
        role2.setId(2L);

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role1), Optional.of(role2));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(existingUser);

        userService.updateUserRoles(json);

        assertEquals(2, existingUser.getRoles().size());
    }

    @Test
    public void testUpdateUsersRoles() {
        UserEditRolesDTO userEditRolesDTO = new UserEditRolesDTO();
        userEditRolesDTO.setId(1L);
        userEditRolesDTO.setRoles(new ArrayList<>());

        List<UserEditRolesDTO> usersEdit = new ArrayList<>();
        usersEdit.add(userEditRolesDTO);

        User existingUser = new User();
        existingUser.setId(1L);

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(existingUser);

        userService.updateUsersRoles(usersEdit);

        assertEquals(userEditRolesDTO.getRoles(), existingUser.getRoles());
    }

}


