package org.softuni.pastryShop.repository;

import org.softuni.pastryShop.model.entities.Order;
import org.softuni.pastryShop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
