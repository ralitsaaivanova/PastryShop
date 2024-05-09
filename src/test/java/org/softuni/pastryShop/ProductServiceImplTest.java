package org.softuni.pastryShop;

import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.Measure;
import org.softuni.pastryShop.model.entities.Product;
import org.softuni.pastryShop.repository.CategoryRepository;
import org.softuni.pastryShop.repository.CurrencyRepository;
import org.softuni.pastryShop.repository.MeasureRepository;
import org.softuni.pastryShop.repository.ProductRepository;
import org.softuni.pastryShop.service.impl.ProductServiceImpl;
import org.softuni.pastryShop.util.ImageEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private MeasureRepository measureRepository;

    @Mock
    private ImageEncryptor imageEncryptor;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws IOException {
        ProductDTO bindingModel = new ProductDTO();
        bindingModel.setName("Test Product");
        bindingModel.setCategoryId(1L);
        bindingModel.setCurrencyId(1L);
        bindingModel.setMeasureId(1L);

        // Set a non-null MultipartFile for the photo
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photo", "test.jpg", "image/jpeg", "Mock photo content".getBytes());
        bindingModel.setPhoto(mockMultipartFile);

        Category category = new Category();
        category.setId(1L);

        Currency currency = new Currency();
        currency.setId(1L);

        Measure measure = new Measure();
        measure.setId(1L);

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(currencyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currency));
        when(measureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(measure));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());

        productService.addProduct(bindingModel);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void testDelete() {
        productService.delete(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.eq(1L));
    }

    @Test
    void testGetProductDTOById() throws IOException {
        // Mock a Product with a non-null Category
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        Currency currency = new Currency();
        currency.setId(1L);
        product.setCurrency(currency);

        Measure measure = new Measure();
        measure.setId(1L);
        product.setMeasure(measure);

        // Mock repository behavior
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call the service method
        Optional<ProductDTO> bindingModelOptional = Optional.ofNullable(productService.getProductDTOById(1L));

        // Assertions
        assertTrue(bindingModelOptional.isPresent());
        ProductDTO bindingModel = bindingModelOptional.get();
        assertEquals(product.getName(), bindingModel.getName());
        assertEquals(product.getCategory().getId(), bindingModel.getCategoryId());
    }

//    @Test
//    public void testGetProductDisplayDTOById() {
//        Product product = new Product();
//        product.setId(1L);
//
//        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
//        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);
//
//        ProductDisplayDTO result = productService.getProductDisplayDTOById(1L);
//
//        assertEquals(1L, result.getId());
//    }

//    @Test
//    public void testGetAllProductsByCategory() {
//        Long categoryId = 1L;
//        Pageable pageable = PageRequest.of(0, 10);
//
//        Category category = new Category();
//        category.setId(categoryId);
//
//        Product product1 = new Product();
//        product1.setId(1L);
//        product1.setCategory(category);
//
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setCategory(category);
//
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
//        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);
//
//        Page<ProductDisplayDTO> result = productService.getAllProductsByCategory(categoryId, pageable);
//
//        assertEquals(2, result.getContent().size());
//        assertEquals(1L, result.getContent().get(0).getId());
//        assertEquals(2L, result.getContent().get(1).getId());
//    }

    @Test
    void testRemove() {
        productService.remove(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }
    @Test
    public void testRemoveAll() {
        productService.removeAll();

        Mockito.verify(productRepository, Mockito.times(1)).deleteAll();
    }


    @Test
    void testGetAll() throws IOException {
        // Mock data
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setWeight(100.0);
        product.setCurrency(new Currency());
        product.setMeasure(new Measure());
        product.setCategory(new Category());
        product.setPhoto("photo_data".getBytes());

        Pageable pageable = Pageable.unpaged();

        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(product)));

        // Test method
        Page<ProductDisplayDTO> result = productService.getAll(pageable);

        // Assertions
        assertEquals(1, result.getContent().size());
        assertEquals(product.getName(), result.getContent().get(0).getName());
        assertEquals(product.getPrice(), result.getContent().get(0).getPrice());
        assertEquals(product.getWeight(), result.getContent().get(0).getWeight());
        assertEquals("photo_data", result.getContent().get(0).getPhoto());
    }
    @Test
    public void testMapProductDTOToProduct() throws IOException {
        ProductDTO bindingModel = new ProductDTO();
        bindingModel.setName("Test Product");
        bindingModel.setPrice(100.0);
        bindingModel.setWeight(120.0);
        bindingModel.setCurrencyId(1L);
        bindingModel.setMeasureId(1L);
        bindingModel.setCategoryId(1L);
        bindingModel.setCategoryId(1L);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photo", "test.jpg", "image/jpeg", "Mock photo content".getBytes());
        bindingModel.setPhoto(mockMultipartFile);


        Category category = new Category();
        category.setId(1L);

        Currency currency = new Currency();
        currency.setId(1L);

        Measure measure = new Measure();
        measure.setId(1L);

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(currencyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currency));
        when(measureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(measure));


        when(imageEncryptor.EncryptImage(Mockito.any())).thenReturn(null);

        Product result = productService.mapProductDTOtoProduct(bindingModel);

        assertEquals("Test Product", result.getName());
        assertEquals(1L, result.getCategory().getId());
    }



}
