package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="categories")
public class Category extends BaseEntity{

    private String name;
    @ManyToOne
    private User user;
}
