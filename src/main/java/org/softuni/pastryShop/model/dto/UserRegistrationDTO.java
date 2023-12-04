package org.softuni.pastryShop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.softuni.pastryShop.model.enums.Role;

public record UserRegistrationDTO(@NotNull @Email String email,
                                  @NotNull @Size(min = 3, max = 20, message = "ERROR!") String username,
                                  Role role,
                                  String password,
                                  String confirmPassword) {
}
