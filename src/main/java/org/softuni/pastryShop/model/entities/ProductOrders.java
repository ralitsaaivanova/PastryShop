package org.softuni.pastryShop.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products_orders")
public class ProductOrders extends BaseEntity{

    @OneToOne
    private Product product;

    @OneToOne
    private Order order;
     //TODO: Fix relations

}
