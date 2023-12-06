package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;


import java.util.List;

public interface CategoryService {
    List<Category> getAll();
}
