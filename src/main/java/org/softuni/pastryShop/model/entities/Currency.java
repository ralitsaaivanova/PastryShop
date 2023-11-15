package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="currencies")
public class Currency extends BaseEntity{
    private String name;
    private String shortName;
    private Double conversionRate;

}
