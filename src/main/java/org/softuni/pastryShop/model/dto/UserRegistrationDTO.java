package org.softuni.pastryShop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.softuni.pastryShop.model.enums.Role;
//import org.softuni.pastryShop.validation.FieldMatch;

//@FieldMatch(
//        first = "password",
//        second = "confirmPassword",
//        message = "Passwords should match."
//)
public record UserRegistrationDTO(@NotNull @Email String email,
                                  @NotNull String username,
                                  Role role,
                                  String password,
                                  String confirmPassword) {
}
