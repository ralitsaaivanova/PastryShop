package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.softuni.pastryShop.model.enums.RoleEnum;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    public String getRoleName() {
        return role.toString();
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }


}
