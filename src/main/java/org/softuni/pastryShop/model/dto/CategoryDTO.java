package org.softuni.pastryShop.model.dto;

import org.softuni.pastryShop.model.entities.User;

public class CategoryDTO {
    private Long id;
    private String categoryName;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static CategoryDTO empty() {
        return new CategoryDTO();
    }
}
