package org.softuni.pastryShop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.model.enums.RoleEnum;
import org.softuni.pastryShop.validation.UniqueUserEmail;
import org.softuni.pastryShop.validation.FieldMatch;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords should match."
)
public record UserRegistrationDTO(@NotNull @Email @UniqueUserEmail String email,
                                  @NotEmpty String username,
                                  String password,
                                  String confirmPassword) {
}
