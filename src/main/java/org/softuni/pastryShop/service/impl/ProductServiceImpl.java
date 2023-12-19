package org.softuni.pastryShop.service.impl;

import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Product;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.*;
import org.softuni.pastryShop.service.ProductService;
import org.softuni.pastryShop.util.ImageEncryptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void addProduct(ProductDTO productDTO) throws IOException {
        if(productDTO!=null){
            Product product = mapProductDTOtoProduct(productDTO);
            productRepository.save(product);
        }
    }

    @Override
    public void update(ProductDTO productDTO) throws IOException {
        if (productDTO.getId() > 0) {
            Product product = mapProductDTOtoProduct(productDTO);
            if(product.getPhoto()==null){
                product.setPhoto(this.productRepository.findById(productDTO.getId()).get().getPhoto());
            }

            productRepository.save(product);
        }
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductDTOById(long id) throws IOException {
        return productRepository.findById(id).map(x -> {
            try {
                return mapProductToProductDTO(x);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).get();
    }

    @Override
    public Page<ProductDisplayDTO> getAllProductsByCategory(Long categoryId, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        List<ProductDisplayDTO> productDisplayDTOs = productRepository.findAll()
                .stream().filter(x -> x.getCategory().getId() == categoryId)
                .map(this::mapProductToProductDisplayDTO)
                .toList();

        return new PageImpl<>(productDisplayDTOs);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        productRepository.deleteAll();
    }

    @Override
    public Page<ProductDisplayDTO> getAll(Pageable pageable) {
        List<ProductDisplayDTO> productDisplayDTOs=productRepository.findAll()
                .stream().map(this::mapProductToProductDisplayDTO)
                .toList();
        return new PageImpl<>(productDisplayDTOs);
    }

    @Override
    public List<Product> getAllByCategory(String id) {
        return this.productRepository.findAllByCategoryId(Long.parseLong(id));
    }

    public Product mapProductDTOtoProduct(ProductDTO productDTO) throws IOException {
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCurrency(this.currencyRepository.findById(productDTO.getCurrencyId()).get());
        product.setMeasure(this.measureRepository.findById(productDTO.getMeasureId()).get());
        product.setCategory(this.categoryRepository.findById(productDTO.getCategoryId()).get());
        product.setDeleted(false);
        product.setPhoto(productDTO.getPhoto().isEmpty() ? null:this.imageEncryptor.EncryptImage(productDTO.getPhoto()));

        return product;
    }

    public ProductDTO mapProductToProductDTO(Product product) throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setCurrencyId(product.getCurrency().getId());
        productDTO.setMeasureId(product.getMeasure().getId());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPhoto(productDTO.getPhoto());
        productDTO.setDisplayPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(product.getPhoto()));

        return productDTO;
    }

    public ProductDisplayDTO mapProductToProductDisplayDTO(Product product) {
        ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
        productDisplayDTO.setId(product.getId());
        productDisplayDTO.setName(product.getName());
        productDisplayDTO.setPrice(product.getPrice());
        productDisplayDTO.setWeight(product.getWeight());
        productDisplayDTO.setCurrencyId(product.getCurrency().getId());
        productDisplayDTO.setMeasureId(product.getMeasure().getId());
        productDisplayDTO.setCategoryId(product.getCategory().getId());
        productDisplayDTO.setPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(product.getPhoto()));

        return productDisplayDTO;
    }

}
