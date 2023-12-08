package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.CategoryRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void addCategory(String username, CategoryDTO categoryDTO) {
        User user = null;
//        if (username != null) {
//            userRepository.findByUsername(username);
//        }

        if (categoryDTO != null) {
            Category category = map(categoryDTO);
            categoryRepository.save(category);
        }
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public void removeAll() {
        categoryRepository.deleteAll();

    }

    private Category map(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getCategoryName());
        category.setUser(this.userRepository.findByUsername(categoryDTO.getUsername()));
        return category;
    }
}

