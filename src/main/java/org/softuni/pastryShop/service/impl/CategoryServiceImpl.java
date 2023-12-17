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
    public void addCategory(CategoryDTO categoryDTO, UserDetails user) {
        Category category = map(categoryDTO);
        User userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + user.getUsername() + " not found!"));


        category.setUser(userEntity);
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

    private Category map(CategoryDTO categoryDTO) {
        User user = userRepository.findByUsername(categoryDTO.getUsername().getUsername());
        Category category = new Category();
        category.setName(categoryDTO.getCategoryName());
        category.setUser(user);
        return category;
    }


}

