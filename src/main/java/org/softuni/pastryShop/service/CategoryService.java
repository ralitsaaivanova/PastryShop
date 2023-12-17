package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    void addCategory(CategoryDTO categoryDTO, UserDetails user);

    void remove(Long id);
    void removeAll();
}
