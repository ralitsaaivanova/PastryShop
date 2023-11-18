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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
