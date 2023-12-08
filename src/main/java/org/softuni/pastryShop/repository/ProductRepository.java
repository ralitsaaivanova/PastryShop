package org.softuni.pastryShop.repository;

import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);
}
