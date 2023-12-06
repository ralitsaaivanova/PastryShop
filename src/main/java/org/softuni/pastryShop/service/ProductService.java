package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    void addProduct(String username, ProductDTO productDTO) throws IOException;

    void remove(Long id);

    void removeAll();
}
