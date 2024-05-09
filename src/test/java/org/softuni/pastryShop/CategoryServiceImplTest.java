package org.softuni.pastryShop;


import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.CategoryRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.impl.CategoryServiceImpl;
import org.softuni.pastryShop.util.ImageEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategory() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("New Category");
        categoryDTO.setUsername("user1");

        User user = new User();
        user.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(user);

        categoryService.addCategory(categoryDTO);

        verify(categoryRepository, times(1)).save(any(Category.class));

    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.getCategoryById(categoryId);

        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getCategoryName());
    }


    @Test
    void testGetAll() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<CategoryDTO> result = categoryService.getAll();

        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getCategoryName());
        assertEquals("Category 2", result.get(1).getCategoryName());
    }

    @Test
    void testRemove() {
        Long categoryId = 1L;

        categoryService.remove(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testRemoveAll() {
        categoryService.removeAll();

        verify(categoryRepository, times(1)).deleteAll();
    }

    @Test
    void testMapCategoryDTOtoCategory() {
        // Mock data
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Test Category");
        categoryDTO.setUsername("user1");

        User user = new User();
        user.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(user);

        // Test method
        Category result = categoryService.mapCategoryDTOtoCategory(categoryDTO);

        // Assertions
        assertEquals(categoryDTO.getCategoryName(), result.getName());
        assertEquals(user, result.getUser());
    }

    @Test
    void testMapCategoryToCategoryDTO() {
        // Mock data
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Test method
        CategoryDTO result = categoryService.mapCategoryToCategoryDTO(category);

        // Assertions
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getCategoryName());
    }


}
