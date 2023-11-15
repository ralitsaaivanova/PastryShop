package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
    private User user;

    /*@ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "orders")*/

    @Transient
    private List<Product>products;
    private Double discount;
    private Double price;
    private Double finalPrice;

}
