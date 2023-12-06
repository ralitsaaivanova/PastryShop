package org.softuni.pastryShop.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "measures")
public class Measure extends BaseEntity {
    private String name;
    private String shortName;
    private boolean isDefault;

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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
