package org.softuni.pastryShop.model.dto;

public class CategoryDTO {
    private String categoryName;
    private String username;

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
