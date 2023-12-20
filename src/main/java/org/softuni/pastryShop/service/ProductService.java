package org.softuni.pastryShop.service;

import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.model.entities.Currency;
import org.softuni.pastryShop.model.entities.Product;
import org.softuni.pastryShop.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(ProductDTO productDTO) throws IOException;

    void update(ProductDTO productDTO) throws IOException;
    void delete(long id);

    ProductDisplayDTO getProductDisplayDTOById(long id);
    ProductDTO getProductDTOById(long id) throws IOException;

    Page<ProductDisplayDTO> getAllProductsByCategory(Long categoryId, Pageable pageable);

    void remove(Long id);

    void removeAll();

    Page<ProductDisplayDTO> getAll(Pageable pageable);

    List<Product> getAllByCategory(String id);

    ProductDisplayDTO mapProductToProductDisplayDTO(Product product);
}
