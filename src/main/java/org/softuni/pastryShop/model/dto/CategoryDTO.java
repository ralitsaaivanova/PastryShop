package org.softuni.pastryShop.model.dto;

import org.softuni.pastryShop.model.entities.User;

public class CategoryDTO {
    private String categoryName;
    private User username;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public static CategoryDTO empty() {
        return new CategoryDTO();
    }
}
