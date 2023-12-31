package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.CategoryRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.CategoryService;
import org.softuni.pastryShop.util.ImageEncryptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).get();


        return mapCategoryToCategoryDTO(category);
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = mapCategoryDTOtoCategory(categoryDTO);

        categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        categoryRepository.deleteAll();
    }

    private Category mapCategoryDTOtoCategory(CategoryDTO categoryDTO) {
        User user = userRepository.findByUsername(categoryDTO.getUsername().getUsername());
        Category category = new Category();
        category.setName(categoryDTO.getCategoryName());
        category.setUser(user);
        return category;
    }

    private CategoryDTO mapCategoryToCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(category.getName());
        categoryDTO.setUsername(categoryDTO.getUsername());
        return categoryDTO;
    }





}

