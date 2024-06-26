package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface CategoryService {

    CategoryDTO getCategoryById(Long id);
    List<CategoryDTO> getAll();

    void addCategory(CategoryDTO categoryDTO);

    void remove(Long id);
    void removeAll();
}
