package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(String username, ProductDTO productDTO) throws IOException;

    void remove(Long id);

    void removeAll();

    List<Product> getAll();

    List<Product> getAllByCategory(String id);
}
