package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.entities.Product;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.*;
import org.softuni.pastryShop.service.ProductService;
import org.softuni.pastryShop.util.ImageEncryptor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MeasureRepository measureRepository;
    private final CurrencyRepository currencyRepository;
    private final ImageEncryptor imageEncryptor;
    private final UserRepository userRepository;


    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              MeasureRepository measureRepository,
                              CurrencyRepository currencyRepository,
                              UserRepository userRepository,
                              ImageEncryptor imageEncryptor) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.measureRepository = measureRepository;
        this.currencyRepository = currencyRepository;
        this.imageEncryptor = imageEncryptor;
        this.userRepository = userRepository;
    }

    @Override
    public void addProduct(String username, ProductDTO productDTO) throws IOException {
        User user = null;
        if (username != null) {
            user = userRepository.findByUsername(username);
        }

        if (productDTO != null) {
            Product product = map(productDTO);
            productRepository.save(product);
        }

    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        productRepository.deleteAll();
    }

    private Product map(ProductDTO productDTO) throws IOException {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCurrency(this.currencyRepository.findById(productDTO.getCurrencyId()).get());
        product.setMeasure(this.measureRepository.findById(productDTO.getMeasureId()).get());
        product.setCategory(this.categoryRepository.findById(productDTO.getCategoryId()).get());
//        product.setCategory(productDTO.getCategory());
        product.setDeleted(false);
        product.setPhoto(this.imageEncryptor.EncryptImage(productDTO.getPhoto()));

        return product;
    }

}
