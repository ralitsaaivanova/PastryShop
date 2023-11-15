package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(nullable = false)
    private String name;
    private Double price;
    private Double weight;
    private byte[] photo;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Measure measure;

    @ManyToOne
    private Category category;

    private boolean isDeleted;

    /*@ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "products_orders",
                joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private List<Order> orders;*/


}
