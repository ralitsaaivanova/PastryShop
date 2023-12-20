package org.softuni.pastryShop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.softuni.pastryShop.model.entities.Role;
import org.softuni.pastryShop.model.enums.RoleEnum;
import org.softuni.pastryShop.validation.UniqueUserEmail;
import org.softuni.pastryShop.validation.FieldMatch;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords should match."
)
public record UserRegistrationDTO(@NotNull(message = "Email should not be empty")
                                  @Email(message = "Not a valid email address")
                                  @UniqueUserEmail(message = "There is already a registered user with that email")
                                  String email,
                                  @NotEmpty(message = "Username should not be empty") String username,
                                  @Length(min = 6, max = 30, message = "Password should be at least 6 symbols")
                                  String password,
                                  String confirmPassword) {
}
