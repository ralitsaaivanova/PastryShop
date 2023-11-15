package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.softuni.pastryShop.model.enums.Role;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{


    @Column(unique = true, nullable = false)
    @Length(min = 3, max = 20)
    private String username;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
