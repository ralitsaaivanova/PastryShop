package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="currencies")
public class Currency extends BaseEntity{
    private String name;
    private String shortName;
    private Double conversionRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
